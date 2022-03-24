package com.exasol.udfstartuptimeimprover;

import java.util.Objects;

import com.exasol.*;
import com.exasol.bucketfs.UnsynchronizedBucket;
import com.exasol.bucketfs.WriteEnabledBucket;
import com.exasol.errorreporting.ExaError;

/**
 * This class is the entrypoint of the java startup time improver UDF.
 */
public class UdfStartUpTimeImprover {
    /** Name for the UDF parameter that contains the definition of the UDF / adapter script to optimize. */
    public static final String PARAMETER_UDF_DEFINITION = "UDF_DEFINITION";

    /** Name for the UDF parameter that contains the name of the schema of the UDF. */
    public static final String PARAMETER_UDF_SCHEMA = "UDF_SCHEMA";
    /**
     * Name of the UDF parameter that connections the name of a connection that stores the write-password of the
     * BucketFS as 'IDENTIFIED BY'.
     */
    public static final String PARAMETER_CONNECTION = "CONNECTION_NAME";
    /** Name for the UDF parameter that contains the BucketFS port. */
    public static final String PARAMETER_BUCKET_FS_PORT = "BUCKET_FS_PORT";
    /** Name for the UDF parameter that contains the BucketFS service name */
    public static final String PARAMETER_BUCKET_FS_SERVICE = "BUCKET_FS_SERVICE";
    /** Name for the UDF parameter that contains the BucketFS bucket name. */
    public static final String PARAMETER_BUCKET_FS_BUCKET = "BUCKET_FS_BUCKET";
    /**
     * Name for the UDF parameter that contains destination path in BucketFS where this UDF writes the class dump to.
     */
    public static final String PARAMETER_PATH_FOR_DUMP = "PATH_FOR_DUMP";

    private UdfStartUpTimeImprover() {
        // static class
    }

    /**
     * Entrypoint of the startup time improver UDF.
     * 
     * @param exaMetadata exasol metadata
     * @param exaIterator iterator
     * 
     * @throws Exception if something goes wrong
     */
    @SuppressWarnings({ "java:S112", "java:S1130" }) // Exception is too generic and not thrown. This signature is
    // however given by the UDF framework
    public static String run(final ExaMetadata exaMetadata, final ExaIterator exaIterator) throws Exception {
        final String udfDefinitionString = readStringParameter(exaIterator, PARAMETER_UDF_DEFINITION);
        final String schema = readStringParameter(exaIterator, PARAMETER_UDF_SCHEMA);
        final String connectionName = readStringParameter(exaIterator, PARAMETER_CONNECTION);
        final ExaConnectionInformation bucketFsConnection = getConnection(exaMetadata, connectionName);
        final String bfsService = readStringParameter(exaIterator, PARAMETER_BUCKET_FS_SERVICE);
        final String bfsBucket = readStringParameter(exaIterator, PARAMETER_BUCKET_FS_BUCKET);
        final String pathForDump = readStringParameter(exaIterator, PARAMETER_PATH_FOR_DUMP);
        final int bfsPort = readIntParameter(exaIterator, PARAMETER_BUCKET_FS_PORT);
        final UnsynchronizedBucket bucket = WriteEnabledBucket.builder().useTls(false).raiseTlsErrors(false)
                .ipAddress("127.0.0.1").port(bfsPort).serviceName(bfsService).name(bfsBucket).readPassword("")
                .writePassword(bucketFsConnection.getPassword()).build();
        return new UdfStartUpTimeImproverInt().run(udfDefinitionString, schema, bucket, pathForDump);
    }

    private static ExaConnectionInformation getConnection(final ExaMetadata exaMetadata, final String connectionName) {
        try {
            return exaMetadata.getConnection(connectionName);
        } catch (final ExaConnectionAccessException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("E-USTI-13")
                    .message("Could not read connection {{connection}}.", connectionName)
                    .mitigation("Make sure that you set '" + PARAMETER_CONNECTION
                            + "' to the name of a connection definition for BucketFS.")
                    .toString(), exception);
        }
    }

    private static String readStringParameter(final ExaIterator iterator, final String parameterName) {
        try {
            return Objects.requireNonNull(iterator.getString(parameterName));
        } catch (final ExaIterationException | ExaDataTypeException | NullPointerException exception) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-USTI-12")
                    .message("Could not read required parameter {{name}}.", parameterName)
                    .mitigation("Make sure that you provide that parameter and that it's of type VARCHAR.").toString(),
                    exception);
        }
    }

    private static int readIntParameter(final ExaIterator iterator, final String parameterName) {
        try {
            return Objects.requireNonNull(iterator.getInteger(parameterName));
        } catch (final ExaIterationException | ExaDataTypeException | NullPointerException exception) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-USTI-15")
                    .message("Could not read required parameter {{name}}.", parameterName)
                    .mitigation("Make sure that you provide that parameter and that it's of type INTEGER.").toString(),
                    exception);
        }
    }
}
