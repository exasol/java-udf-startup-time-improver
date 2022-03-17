package com.exasol.udfstartuptimeimprover;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.exasol.bucketfs.BucketAccessException;
import com.exasol.bucketfs.UnsynchronizedBucket;
import com.exasol.errorreporting.ExaError;

/**
 * Implementation of the start-up time improver.
 */
class UdfStartUpTimeImproverInt {
    private static final String DUMP_IN_TMP = "/tmp/dump.jsa";
    private static final String CLASSES_LIST_FILE_NAME = "classes.lst";
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

    public String run(final String udfDefinitionString, final UnsynchronizedBucket bucket, final String pathForDump) {
        final UdfDefinition udfDefinition = new UdfDefinitionParser().parseUdfDefinition(udfDefinitionString);
        final Path classListPath = extractClassList(udfDefinition);
        runClassDumpGeneration(udfDefinition.getJars(), classListPath);
        uploadDump(bucket, pathForDump);
        return rewriteUdfDefinition(udfDefinition, bucket.getBucketFsName(), bucket.getBucketName(), pathForDump);
    }

    private Path extractClassList(final UdfDefinition udfDefinition) {
        final List<String> classLists = udfDefinition.getJars().stream().flatMap(this::findClassListInJar)
                .collect(Collectors.toList());
        if (classLists.isEmpty()) {
            throw new IllegalStateException(ExaError.messageBuilder("E-USTI-17")
                    .message("None of the jars of this script contained a " + CLASSES_LIST_FILE_NAME + " file.")
                    .mitigation(
                            "Please check that the jar you're trying to optimize is \"Prepared for Java UDF startup time improver\".")
                    .toString());
        }
        final String combinedClassList = combineClassLists(classLists);
        return writeClassListToTmp(combinedClassList);
    }

    private Path writeClassListToTmp(final String combinedClassList) {
        final Path classListPath = Path.of("/tmp/classes.lst");
        try {
            Files.writeString(classListPath, combinedClassList);
        } catch (final IOException exception) {
            throw new IllegalStateException(
                    ExaError.messageBuilder("F-USTI-18")
                            .message("Failed to write temporary class list to {{path}}.", classListPath).toString(),
                    exception);
        }
        return classListPath;
    }

    private String combineClassLists(final List<String> classLists) {
        return classLists.stream().flatMap(list -> Arrays.stream(list.split("\n")).map(String::trim))
                .collect(Collectors.joining("\n"));
    }

    private Stream<String> findClassListInJar(final String jarName) {
        final Path jarFile = Path.of(this.rootDirOffset + jarName);
        try (final FileInputStream inputStream = new FileInputStream(jarFile.toFile());
                final BufferedInputStream bufferedStream = new BufferedInputStream(inputStream);
                final ZipInputStream zipInputStream = new ZipInputStream(bufferedStream)) {
            ZipEntry entry = zipInputStream.getNextEntry();
            while (entry != null) {
                if (CLASSES_LIST_FILE_NAME.equals(entry.getName())) {
                    return Stream.of(new String(zipInputStream.readAllBytes(), StandardCharsets.UTF_8));
                }
                entry = zipInputStream.getNextEntry();
            }
            return Stream.empty();
        } catch (final IOException exception) {
            throw new UncheckedIOException(ExaError.messageBuilder("E-USTI-16")
                    .message("Failed to read {{file}} for extracting the " + CLASSES_LIST_FILE_NAME + ".", jarFile)
                    .toString(), exception);
        }
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
}
