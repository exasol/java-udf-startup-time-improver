package com.exasol.udfstartuptimeimprover;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UdfDefinitionParserTest {
    private static final UdfDefinitionParser PARSER = new UdfDefinitionParser();

    @Test
    void testParse() {
        final UdfDefinition result = PARSER
                .parseUdfDefinition("CREATE JAVA SCALAR SCRIPT \"MY_UDF\" (...) RETURNS VARCHAR(50) UTF8 AS\n"
                        + "%scriptclass com.exasol.testudf.MyUdf;\n"//
                        + "%jvmoption -Xms128m -Xmx1024m -Xss512k;\n"//
                        + "%jar /buckets/bfsdefault/default/udf-for-testing.jar;\n\n");
        assertAll(//
                () -> assertThat(result.getCreateStatementStart(), equalTo("CREATE JAVA SCALAR SCRIPT")),
                () -> assertThat(result.getName(), equalTo("MY_UDF")),
                () -> assertThat(result.getCreateStatementEnd(), equalTo("(...) RETURNS VARCHAR(50) UTF8 AS")),
                () -> assertThat(result.getScriptClass(), equalTo("com.exasol.testudf.MyUdf")),
                () -> assertThat(result.getJars(), contains("/buckets/bfsdefault/default/udf-for-testing.jar")),
                () -> assertThat(result.getJvmOptions(), containsInAnyOrder("-Xms128m", "-Xmx1024m", "-Xss512k"))//
        );
    }

    @Test
    void testParseWithScriptNameContainingAs() {
        final UdfDefinition result = PARSER.parseUdfDefinition(
                "CREATE JAVA SCALAR SCRIPT \"MY \"\"UDF\"\" WITH AS IN THE NAME\" (...) RETURNS VARCHAR(50) UTF8 AS\n"
                        + "%scriptclass com.exasol.testudf.MyUdf;\n"//
                        + "%jar /buckets/bfsdefault/default/udf-for-testing.jar;\n\n");
        assertThat(result.getScriptClass(), equalTo("com.exasol.testudf.MyUdf"));
    }

    @Test
    void testParseAdapterScript() {
        final UdfDefinition result = PARSER
                .parseUdfDefinition("CREATE JAVA ADAPTER SCRIPT SCHEMA_FOR_VS_SCRIPT.ADAPTER_SCRIPT_EXASOL AS\n"
                        + "    %scriptclass com.exasol.adapter.RequestDispatcher;\n"
                        + "    %jar /buckets/bfsdefault/default/virtual-schema-dist-9.0.4-exasol-6.0.3.jar;\n\n");
        assertAll(//
                () -> assertThat(result.getScriptClass(), equalTo("com.exasol.adapter.RequestDispatcher")),
                () -> assertThat(result.getJars(),
                        contains("/buckets/bfsdefault/default/virtual-schema-dist-9.0.4-exasol-6.0.3.jar")));
    }

    @Test
    void testParseWithRepeatedOptions() {
        final UdfDefinition result = PARSER
                .parseUdfDefinition("CREATE JAVA SCALAR SCRIPT \"MY_UDF\" (...) RETURNS VARCHAR(50) UTF8 AS\n"
                        + "%scriptclass com.exasol.testudf.MyUdf;\n"//
                        + "%jvmoption -Xms128m -Xmx1024m;\n"//
                        + "%jvmoption -Xss512k;\n"//
                        + "%jar /buckets/bfsdefault/default/other.jar;\n"
                        + "%jar /buckets/bfsdefault/default/udf-for-testing.jar;\n\n");
        assertAll(//
                () -> assertThat(result.getScriptClass(), equalTo("com.exasol.testudf.MyUdf")),
                () -> assertThat(result.getJars(),
                        containsInAnyOrder("/buckets/bfsdefault/default/udf-for-testing.jar",
                                "/buckets/bfsdefault/default/other.jar")),
                () -> assertThat(result.getJvmOptions(), containsInAnyOrder("-Xms128m", "-Xmx1024m", "-Xss512k"))//
        );
    }

    @Test
    void testNoAs() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> PARSER.parseUdfDefinition("CREATE JAVA SCALAR SCRIPT \"MY_UDF\" (...) RETURNS VARCHAR(50) UTF8"
                        + "%jar /buckets/bfsdefault/default/udf-for-testing.jar;\n\n"));
        assertThat(exception.getMessage(), containsString("Could not find keyword ' AS'."));
    }

    @Test
    void testNoScriptClass() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> PARSER
                        .parseUdfDefinition("CREATE JAVA SCALAR SCRIPT \"MY_UDF\" (...) RETURNS VARCHAR(50) UTF8 AS\n"
                                + "%jar /buckets/bfsdefault/default/udf-for-testing.jar;\n\n"));
        assertThat(exception.getMessage(), containsString("Could not find required '%scriptclass'."));
    }

    @Test
    void testNoJar() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> PARSER
                        .parseUdfDefinition("CREATE JAVA SCALAR SCRIPT \"MY_UDF\" (...) RETURNS VARCHAR(50) UTF8 AS\n"
                                + "%scriptclass com.exasol.testudf.MyUdf;\n\n"));
        assertThat(exception.getMessage(), containsString("Could not find a '%jar' definition."));
    }

    @Test
    void testAdditionalContent() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> PARSER
                        .parseUdfDefinition("CREATE JAVA SCALAR SCRIPT \"MY_UDF\" (...) RETURNS VARCHAR(50) UTF8 AS\n"
                                + "%scriptclass com.exasol.testudf.MyUdf;\n"
                                + "%jar /buckets/bfsdefault/default/udf-for-testing.jar;\n"//
                                + "class Test{ }\n"));
        assertThat(exception.getMessage(), containsString("Unexpected line 'class Test{ }'."));
    }

    @Test
    void testMissingSemicolon() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> PARSER
                        .parseUdfDefinition("CREATE JAVA SCALAR SCRIPT \"MY_UDF\" (...) RETURNS VARCHAR(50) UTF8 AS\n"
                                + "%scriptclass com.exasol.testudf.MyUdf\n"
                                + "%jar /buckets/bfsdefault/default/udf-for-testing.jar;\n\n"));
        assertThat(exception.getMessage(),
                containsString("Missing terminating ';' in '%scriptclass com.exasol.testudf.MyUdf'."));
    }

    @Test
    void testScriptClassSetTwice() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> PARSER
                        .parseUdfDefinition("CREATE JAVA SCALAR SCRIPT \"MY_UDF\" (...) RETURNS VARCHAR(50) UTF8 AS\n"
                                + "%scriptclass com.exasol.testudf.MyUdf;\n"//
                                + "%scriptclass com.exasol.testudf.Other;\n"//
                                + "%jar /buckets/bfsdefault/default/udf-for-testing.jar;\n\n"));
        assertThat(exception.getMessage(), containsString("'%scriptclass' must only be set once."));
    }

    @Test
    void testNoStartingWithCreate() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> PARSER.parseUdfDefinition("Hi"));
        assertThat(exception.getMessage(), containsString("The script definition must start with 'CREATE'."));
    }
}