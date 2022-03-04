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
        final int indexOfScriptDefinitionStart = getIndexOfScriptDefinitionStart(udfDefinition);
        final String createStatement = udfDefinition.substring(0, indexOfScriptDefinitionStart);
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
        return new UdfDefinition(createStatement, scriptClass, jars, jvmOptions);
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

    private int getIndexOfScriptDefinitionStart(final String definition) {
        boolean isQuoted = false;
        for (int index = 0; index < definition.length(); index++) {
            final char currentChar = definition.charAt(index);
            if (currentChar == '"') {
                isQuoted = !isQuoted;
            }
            if (!isQuoted && index >= 2 && definition.charAt(index - 2) == ' ' && definition.charAt(index - 1) == 'A'
                    && currentChar == 'S') {
                return index + 1;
            }
        }
        throw new IllegalArgumentException(ExaError.messageBuilder("E-USTI-6")
                .message(FAILED_TO_PARSE_SCRIPT_DEFINITION
                        + "Invalid script definition {{definition}}. Could not find keyword 'AS'.", definition)
                .toString());
    }
}
