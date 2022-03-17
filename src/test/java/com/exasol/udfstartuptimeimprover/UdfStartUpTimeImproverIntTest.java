package com.exasol.udfstartuptimeimprover;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exasol.bucketfs.BucketAccessException;
import com.exasol.bucketfs.UnsynchronizedBucket;

@ExtendWith(MockitoExtension.class)
class UdfStartUpTimeImproverIntTest {
    private static final String UDF_DEF_PART_2 = " JAVA SCALAR SCRIPT \"MY_UDF\" (...) RETURNS VARCHAR(50) UTF8 AS\n"
            + "%scriptclass com.exasol.testudf.MyUdf;\n"//
            + "%jvmoption -Xms128m -Xmx1024m -Xss512k;\n"//
            + "%jar /buckets/bfsdefault/default/test.jar;\n\n";
    private static final String UDF_DEF = "CREATE" + UDF_DEF_PART_2;
    private static final String EXAMPLE_CLASS_LIST = "java/lang/Object\njava/io/Serializable\njava/lang/Comparable\njava/lang/CharSequence";
    @TempDir
    Path tempDir;

    @Mock
    UnsynchronizedBucket bucket;

    @ParameterizedTest
    @ValueSource(strings = { "CREATE", "create", "CREATE OR REPLACE" })
    void testRunImprover(final String udfCreateCommandStart)
            throws IOException, BucketAccessException, TimeoutException {
        createJarFile(EXAMPLE_CLASS_LIST);
        when(this.bucket.getBucketName()).thenReturn("default");
        when(this.bucket.getBucketFsName()).thenReturn("bfsdefault");
        final String result = new UdfStartUpTimeImproverInt(this.tempDir.toString())
                .run(udfCreateCommandStart + UDF_DEF_PART_2, this.bucket, "my-dump.jsa");
        assertThat(result, equalTo(
                "CREATE OR REPLACE JAVA SCALAR SCRIPT \"MY_UDF\" (...) RETURNS VARCHAR(50) UTF8 AS\n%scriptclass com.exasol.testudf.MyUdf;\n%jvmoption -Xms128m -Xmx1024m -Xss512k -XX:SharedArchiveFile=/buckets/bfsdefault/default/my-dump.jsa;\n%jar /buckets/bfsdefault/default/test.jar;\n\n"));
        verify(this.bucket).uploadFileNonBlocking(Path.of("/tmp/dump.jsa"), "my-dump.jsa");
    }

    @Test
    void testMissingClassList() throws IOException {
        createJarFile(null);
        final UdfStartUpTimeImproverInt improver = new UdfStartUpTimeImproverInt(this.tempDir.toString());
        final IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> improver.run(UDF_DEF, this.bucket, "my-dump.jsa"));
        assertThat(exception.getMessage(), equalTo(
                "E-USTI-17: None of the jars of this script contained a classes.lst file. Please check that the jar you're trying to optimize is \"Prepared for Java UDF startup time improver\"."));
    }

    @Test
    void testUploadFails() throws IOException, BucketAccessException, TimeoutException {
        createJarFile(EXAMPLE_CLASS_LIST);
        doThrow(new BucketAccessException("")).when(this.bucket).uploadFileNonBlocking(any(), any());
        final UdfStartUpTimeImproverInt improver = new UdfStartUpTimeImproverInt(this.tempDir.toString());
        final IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> improver.run(UDF_DEF, this.bucket, "my-dump.jsa"));
        assertThat(exception.getMessage(), equalTo("E-USTI-14: Failed to upload generated class dump to BucketFS."));
    }

    @Test
    void testOversizeClassList() throws IOException {
        createJarFile("a".repeat(20_000_000));
        final UdfStartUpTimeImproverInt improver = new UdfStartUpTimeImproverInt(this.tempDir.toString());
        final IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> improver.run(UDF_DEF, this.bucket, "my-dump.jsa"));
        assertThat(exception.getMessage(), equalTo("E-USTI-19: The uncompressed class list was larger then 10 MB."));
    }

    @Test
    void testMissingJarFile() {
        final UdfStartUpTimeImproverInt improver = new UdfStartUpTimeImproverInt(this.tempDir.toString());
        final UncheckedIOException exception = assertThrows(UncheckedIOException.class,
                () -> improver.run(UDF_DEF, this.bucket, "my-dump.jsa"));
        assertThat(exception.getMessage(), startsWith("E-USTI-16: Failed to read"));
    }

    private void createJarFile(final String classList) throws IOException {
        final Path dir = this.tempDir.resolve("buckets/bfsdefault/default/");
        Files.createDirectories(dir);
        final Path jar = dir.resolve("test.jar");
        try (final FileOutputStream fileOutputStream = new FileOutputStream(jar.toFile());
                final ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {
            if (classList != null) {
                zipOutputStream.putNextEntry(new ZipEntry("classes.lst"));
                zipOutputStream.write(classList.getBytes(StandardCharsets.UTF_8));
                zipOutputStream.closeEntry();
            }
        }
    }
}