package com.exasol.udfstartuptimeimprover;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class UdfDefinitionTest {

    @Test
    void testToString() {
        final UdfDefinition udfDefinition = new UdfDefinition("CREATE JAVA SCALAR SCRIPT", "TEST", "MY_UDF",
                "(...) RETURNS VARCHAR(50) UTF8 AS", "com.example.testudf.MyUdf",
                List.of("/buckets/bfsdefault/default/test.jar", "/buckets/bfsdefault/default/other.jar"),
                List.of("-Xms128m", "-Xmx1024m"));
        assertThat(udfDefinition.toString(),
                equalTo("CREATE JAVA SCALAR SCRIPT \"TEST\".\"MY_UDF\" (...) RETURNS VARCHAR(50) UTF8 AS\n"
                        + "%scriptclass com.example.testudf.MyUdf;\n"//
                        + "%jvmoption -Xms128m -Xmx1024m;\n"//
                        + "%jar /buckets/bfsdefault/default/test.jar;\n"
                        + "%jar /buckets/bfsdefault/default/other.jar;\n\n"));
    }

    @Test
    void testMinimalToString() {
        final UdfDefinition udfDefinition = new UdfDefinition("CREATE JAVA SCALAR SCRIPT", "TEST", "MY_UDF",
                "(...) RETURNS VARCHAR(50) UTF8 AS", "com.example.testudf.MyUdf",
                List.of("/buckets/bfsdefault/default/test.jar"), Collections.emptyList());
        assertThat(udfDefinition.toString(),
                equalTo("CREATE JAVA SCALAR SCRIPT \"TEST\".\"MY_UDF\" (...) RETURNS VARCHAR(50) UTF8 AS\n"
                        + "%scriptclass com.example.testudf.MyUdf;\n"
                        + "%jar /buckets/bfsdefault/default/test.jar;\n\n"));
    }

    @Test
    void testEqualsContract() {
        EqualsVerifier.forClass(UdfDefinition.class).verify();
    }
}