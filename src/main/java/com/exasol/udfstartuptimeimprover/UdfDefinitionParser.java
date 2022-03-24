package com.exasol.udfstartuptimeimprover;

import java.util.*;

import com.exasol.errorreporting.ExaError;

/**
 * This class parses the UDF or adapter script definition string.
 */
public class UdfDefinitionParser {
    private static final String KEY_SCRIPTCLASS = "%scriptclass ";
    private static final String KEY_JAR = "%jar ";
    private static final String KEY_JVM_OPTION = "%jvmoption ";
    private static final String FAILED_TO_PARSE_SCRIPT_DEFINITION = "Failed to parse script definition (parameter 1): ";
    private static final String KEYWORD_SCRIPT = "SCRIPT";
    private static final String KEYWORD_AS = " AS";

    /**
     * Parse the UDF or adapter script definition string.
     * 
     * @param udfDefinitionString definition string
     * @return parsed definition
     */
    public UdfDefinition parseUdfDefinition(final String udfDefinitionString) {
        assertStartsWithCreate(udfDefinitionString);
        final UdfDefinition udfDefinition = readDefinition(udfDefinitionString);
        assertScriptClassIsSet(udfDefinition.getScriptClass());
        assertJarsNotEmpty(udfDefinition.getJars());
        return udfDefinition;
    }

    private void assertStartsWithCreate(final String udfDefinitionString) {
        if (!udfDefinitionString.toUpperCase().startsWith("CREATE")) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-USTI-1")
                    .message(FAILED_TO_PARSE_SCRIPT_DEFINITION + "The script definition must start with 'CREATE'.")
                    .toString());
        }
    }

    private UdfDefinition readDefinition(final String udfDefinition) {

        final int indexOfNameStart = indexOfUnquoted(udfDefinition, KEYWORD_SCRIPT, 0) + KEYWORD_SCRIPT.length();
        final int indexOfNameEnd = indexOfUnquoted(udfDefinition, " ",
                indexOfNextNonWonWhitespace(udfDefinition, indexOfNameStart));
        final int indexOfScriptDefinitionStart = indexOfUnquoted(udfDefinition, KEYWORD_AS, indexOfNameEnd - 1)
                + KEYWORD_AS.length();
        final String createStatementStart = udfDefinition.substring(0, indexOfNameStart).trim();
        final String name = unquote(udfDefinition.substring(indexOfNameStart, indexOfNameEnd).trim());
        final String createStatementEnd = udfDefinition.substring(indexOfNameEnd, indexOfScriptDefinitionStart).trim();
        final String scriptContent = udfDefinition.substring(indexOfScriptDefinitionStart);
        String scriptClass = null;
        final List<String> jars = new ArrayList<>();
        final List<String> jvmOptions = new ArrayList<>();
        for (final String row : scriptContent.split("\n")) {
            final String trimmedRow = row.trim();
            if (trimmedRow.startsWith(KEY_SCRIPTCLASS)) {
                assertScriptClassIsNotAlreadySet(scriptClass);
                scriptClass = readOption(trimmedRow, KEY_SCRIPTCLASS);
            } else if (trimmedRow.startsWith(KEY_JAR)) {
                jars.add(readOption(trimmedRow, KEY_JAR));
            } else if (trimmedRow.contains(KEY_JVM_OPTION)) {
                jvmOptions.addAll(Arrays.asList(readOption(trimmedRow, KEY_JVM_OPTION).split("\\s")));
            } else if (!trimmedRow.isBlank()) {
                throw getUnexpectedLineException(trimmedRow);
            }
        }
        return new UdfDefinition(createStatementStart, "", name, createStatementEnd, scriptClass, jars, jvmOptions);
    }

    private int indexOfNextNonWonWhitespace(final String subject, final int position) {
        for (int index = position; index < subject.length(); index++) {
            if (subject.charAt(index) != ' ')
                return index;
        }
        return subject.length();
    }

    private String unquote(final String string) {
        if (string.startsWith("\"") && string.endsWith("\"")) {
            final String unquoted = string.substring(1, string.length() - 1);
            return unquoted.replace("\"\"", "\"");
        } else {
            return string.toUpperCase();
        }
    }

    private void assertScriptClassIsNotAlreadySet(final String scriptClass) {
        if (scriptClass != null) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-USTI-2")
                    .message(FAILED_TO_PARSE_SCRIPT_DEFINITION + "'%scriptclass' must only be set once.").toString());
        }
    }

    private IllegalArgumentException getUnexpectedLineException(final String trimmedRow) {
        return new IllegalArgumentException(ExaError.messageBuilder("E-USTI-4")
                .message(FAILED_TO_PARSE_SCRIPT_DEFINITION + "Unexpected line {{line}}.", trimmedRow)
                .mitigation("Probably this script uses inline code. Optimizing those scripts is not supported.")
                .toString());
    }

    private void assertJarsNotEmpty(final List<String> jars) {
        if (jars.isEmpty()) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-USTI-7")
                    .message(FAILED_TO_PARSE_SCRIPT_DEFINITION + "Could not find a '%jar' definition.").toString());
        }
    }

    private void assertScriptClassIsSet(final String scriptClass) {
        if (scriptClass == null || scriptClass.isBlank()) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-USTI-5")
                    .message(FAILED_TO_PARSE_SCRIPT_DEFINITION + "Could not find required '%scriptclass'.").toString());
        }
    }

    private String readOption(final String input, final String key) {
        final int position = input.indexOf(";", key.length());
        if (position == -1) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-USTI-3")
                    .message(FAILED_TO_PARSE_SCRIPT_DEFINITION + "Missing terminating ';' in {{line}}.", input)
                    .toString());
        }
        return input.substring(key.length(), position);
    }

    /**
     * Check if a certain position in a string is inside of quotes.
     * <p>
     * For example for string='test "123"' and position = 0 this method would return {@code false}. For the same string
     * and position = 7 it would return {@code true}.
     * </p>
     * 
     * @param string   string to analyze
     * @param position position to check
     * @return {code true} if the character at the given position is inside of quotes
     */
    private boolean isInQuotes(final String string, final int position) {
        boolean isQuoted = false;
        for (int index = 0; index < string.length(); index++) {
            final char currentChar = string.charAt(index);
            if (currentChar == '"') {
                isQuoted = !isQuoted;
            }
            if (index == position) {
                return isQuoted;
            }
        }
        return false;
    }

    /**
     * Find a substring in another string with ignoring quoted parts.
     * 
     * @param definition    definition to search in
     * @param search        string to search for
     * @param startPosition position to start the search at
     * @return position
     */
    private int indexOfUnquoted(final String definition, final String search, final int startPosition) {
        int index = startPosition;
        while ((index = definition.toUpperCase().indexOf(search.toUpperCase(), index + 1)) != -1) {
            if (!isInQuotes(definition, index)) {
                return index;
            }
        }
        throw new IllegalArgumentException(
                ExaError.messageBuilder("E-USTI-6")
                        .message(FAILED_TO_PARSE_SCRIPT_DEFINITION
                                + "Invalid script definition {{definition}}. Could not find keyword {{keyword}}.",
                                definition, search)
                        .toString());
    }
}
