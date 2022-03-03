package com.exasol.udfstartuptimeimprover;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeoutException;

import com.exasol.*;
import com.exasol.bucketfs.*;
import com.exasol.errorreporting.ExaError;

/**
 * This class is the entrypoint of the java startup time improver UDF.
 */
public class UdfStartUpTimeImprover {
    /** Name for the UDF parameter that contains the definition of the UDF / adapter script to optimize. */
    public static final String PARAMETER_UDF_DEFINITION = "UDF_DEFINITION";
    /** Name for the UDF parameter that contains the path in BucketFS to a file with the classes to preload. */
    public static final String PARAMETER_CLASSES = "CLASSES";
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
    private static final String DUMP_IN_TMP = "/tmp/dump.jsa";

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
        return runInternal(exaMetadata, exaIterator);
    }

    private static String runInternal(final ExaMetadata exaMetadata, final ExaIterator exaIterator) {
        final String udfDefinitionString = readStringParameter(exaIterator, PARAMETER_UDF_DEFINITION);
        final UdfDefinition udfDefinition = new UdfDefinitionParser().parseUdfDefinition(udfDefinitionString);
        final Path pathToClassList = Path.of(readStringParameter(exaIterator, PARAMETER_CLASSES));
        final String connectionName = readStringParameter(exaIterator, PARAMETER_CONNECTION);
        final String bfsService = readStringParameter(exaIterator, PARAMETER_BUCKET_FS_SERVICE);
        final String bfsBucket = readStringParameter(exaIterator, PARAMETER_BUCKET_FS_BUCKET);
        final String pathForDump = readStringParameter(exaIterator, PARAMETER_PATH_FOR_DUMP);
        final int bfsPort = readIntParameter(exaIterator, PARAMETER_BUCKET_FS_PORT);
        final ExaConnectionInformation bucketFsConnection = getConnection(exaMetadata, connectionName);
        assertClassListExists(pathToClassList);
        runClassDumpGeneration(udfDefinition.getJars(), pathToClassList);
        final UnsynchronizedBucket bucket = WriteEnabledBucket.builder().useTls(false).raiseTlsErrors(false)
                .ipAddress("127.0.0.1").port(bfsPort).serviceName(bfsService).name(bfsBucket).readPassword("")
                .writePassword(bucketFsConnection.getPassword()).build();
        uploadDump(bucket, pathForDump);
        return rewriteUdfDefinition(udfDefinition, bfsService, bfsBucket, pathForDump);
    }

    private static String rewriteUdfDefinition(final UdfDefinition udfDefinition, final String bfsService,
            final String bfsBucket, final String pathForDump) {
        final Path pathToDumpForInUdf = Path.of("/buckets/").resolve(bfsService).resolve(bfsBucket)
                .resolve(pathForDump);
        final ArrayList<String> newJvmOptions = new ArrayList<>(udfDefinition.getJvmOptions());
        newJvmOptions.add("-XX:SharedArchiveFile=" + pathToDumpForInUdf);
        final String udfDefWithJvmOption = udfDefinition.withJvmOptions(newJvmOptions).toString();
        if (!udfDefWithJvmOption.startsWith("CREATE OR REPLACE")) {
            return "CREATE OR REPLACE" + udfDefWithJvmOption.substring("CREATE".length());
        } else {
            return udfDefWithJvmOption;
        }
    }

    private static void uploadDump(final UnsynchronizedBucket bucket, final String target) {
        try {
            bucket.uploadFileNonBlocking(Path.of(DUMP_IN_TMP), target);
        } catch (final BucketAccessException | FileNotFoundException | TimeoutException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("E-USTI-14")
                    .message("Failed to upload generated class dump to BucketFS.").toString(), exception);
        }
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
                    .mitigation("Make sure that you provide that parameter and that it's of type VARCHAR.")
                    .toString(), exception);
        }
    }

    private static int readIntParameter(final ExaIterator iterator, final String parameterName) {
        try {
            return Objects.requireNonNull(iterator.getInteger(parameterName));
        } catch (final ExaIterationException | ExaDataTypeException | NullPointerException exception) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-USTI-15")
                    .message("Could not read required parameter {{name}}.", parameterName)
                    .mitigation("Make sure that you provide that parameter and that it's of type INTEGER.")
                    .toString(), exception);
        }
    }

    private static void runClassDumpGeneration(final List<String> jars, final Path pathToClassList) {
        try {
            final List<String> commandParts = new ArrayList<>(
                    List.of("java", "-Xshare:dump", "-XX:SharedClassListFile=" + pathToClassList,
                            "-XX:SharedArchiveFile=" + DUMP_IN_TMP, "--class-path"));
            commandParts.addAll(jars);
            final Process process = Runtime.getRuntime().exec(commandParts.toArray(String[]::new));
            final int exitCode = process.waitFor();
            assertExitCode(process, exitCode);
        } catch (final InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(ExaError.messageBuilder("E-USTI-11")
                    .message("Interrupted while building class archive.").toString(), exception);
        } catch (final IOException exception) {
            throw new IllegalStateException(
                    ExaError.messageBuilder("E-USTI-10").message("Building class archive failed").toString(),
                    exception);
        }
    }

    private static void assertExitCode(final Process process, final int exitCode) throws IOException {
        if (exitCode != 0) {
            final byte[] output = process.getInputStream().readAllBytes();
            throw new IllegalStateException(ExaError.messageBuilder("E-USTI-9")
                    .message("Building class archive failed (exit code was {{exit code}}):{{output}}\n", exitCode,
                            output)
                    .toString());
        }
    }

    private static void assertClassListExists(final Path pathToClassList) {
        if (!Files.exists(pathToClassList)) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-USTI-8")
                    .message("Could not find the specified classes list {{path}} in BucketFS.", pathToClassList)
                    .toString());
        }
    }
}
