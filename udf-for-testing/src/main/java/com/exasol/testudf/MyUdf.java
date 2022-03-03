package com.exasol.testudf;

import com.exasol.ExaIterator;
import com.exasol.ExaMetadata;

/**
 * UDF for testing.
 */
public class MyUdf {

    private MyUdf() {
        // static class
    }

    /**
     * Entrypoint if the Hello World UDF.
     *
     * @param exaMetadata exasol metadata
     * @param exaIterator iterator
     * @throws Exception if data can't get be loaded
     */
    @SuppressWarnings({ "java:S112", "java:S1130" }) // Exception is too generic and not thrown. This signature is
    // however given by the UDF framework
    public static String run(final ExaMetadata exaMetadata, final ExaIterator exaIterator) throws Exception {
        return "Hello World";
    }
}
