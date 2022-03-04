package com.exasol.udfstartuptimeimprover;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.exasol.bucketfs.BucketAccessException;
import com.exasol.bucketfs.UnsynchronizedBucket;
import com.exasol.errorreporting.ExaError;

/**
 * Implementation of the start-up time improver.
 */
class UdfStartUpTimeImproverInt {
    private static final String DUMP_IN_TMP = "/tmp/dump.jsa";
    private final String rootDirOffset;

    UdfStartUpTimeImproverInt() {
        this.rootDirOffset = "";
    }

    /**
     * Alternate constructor for testing.
     * 
     * @param rootDirOffset offset for the filesystem root. Useful for testing.
     */
    UdfStartUpTimeImproverInt(final String rootDirOffset) {
        this.rootDirOffset = rootDirOffset;
    }

    public String run(final String udfDefinitionString, final String pathToClassList, final UnsynchronizedBucket bucket,
            final String pathForDump) {
        final Path pathToClassListWithOffset = Path.of(this.rootDirOffset + pathToClassList);
        final UdfDefinition udfDefinition = new UdfDefinitionParser().parseUdfDefinition(udfDefinitionString);
        assertClassListExists(pathToClassListWithOffset);
        runClassDumpGeneration(udfDefinition.getJars(), pathToClassListWithOffset);
        uploadDump(bucket, pathForDump);
        return rewriteUdfDefinition(udfDefinition, bucket.getBucketFsName(), bucket.getBucketName(), pathForDump);
    }

    private String rewriteUdfDefinition(final UdfDefinition udfDefinition, final String bfsService,
            final String bfsBucket, final String pathForDump) {
        final Path pathToDumpForInUdf = Path.of("/buckets/").resolve(bfsService).resolve(bfsBucket)
                .resolve(pathForDump);
        final ArrayList<String> newJvmOptions = new ArrayList<>(udfDefinition.getJvmOptions());
        newJvmOptions.add("-XX:SharedArchiveFile=" + pathToDumpForInUdf);
        final String udfDefWithJvmOption = udfDefinition.withJvmOptions(newJvmOptions).toString();
        if (!udfDefWithJvmOption.toUpperCase().startsWith("CREATE OR REPLACE")) {
            return "CREATE OR REPLACE" + udfDefWithJvmOption.substring("CREATE".length());
        } else {
            return udfDefWithJvmOption;
        }
    }

    @SuppressWarnings("java:S5443") // writing to tmp is safe in a UDF since it runs in it's own container
    private void uploadDump(final UnsynchronizedBucket bucket, final String target) {
        try {
            bucket.uploadFileNonBlocking(Path.of(DUMP_IN_TMP), target);
        } catch (final BucketAccessException | FileNotFoundException | TimeoutException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("E-USTI-14")
                    .message("Failed to upload generated class dump to BucketFS.").toString(), exception);
        }
    }

    private void runClassDumpGeneration(final List<String> jars, final Path pathToClassList) {
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

    private void assertExitCode(final Process process, final int exitCode) throws IOException {
        if (exitCode != 0) {
            final byte[] output = process.getInputStream().readAllBytes();
            throw new IllegalStateException(ExaError.messageBuilder("E-USTI-9")
                    .message("Building class archive failed (exit code was {{exit code}}):{{output}}\n", exitCode,
                            output)
                    .toString());
        }
    }

    private void assertClassListExists(final Path pathToClassList) {
        if (!Files.exists(pathToClassList)) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-USTI-8")
                    .message("Could not find the specified classes list {{path}} in BucketFS.", pathToClassList)
                    .toString());
        }
    }
}
