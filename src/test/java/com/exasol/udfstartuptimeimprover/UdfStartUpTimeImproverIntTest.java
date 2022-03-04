package com.exasol.udfstartuptimeimprover;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

import org.hamcrest.Matchers;
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
            + "%jar /buckets/bfsdefault/default/udf-for-testing.jar;\n\n";
    private static final String UDF_DEF = "CREATE" + UDF_DEF_PART_2;
    @TempDir
    Path tempDir;

    @Mock
    UnsynchronizedBucket bucket;

    @ParameterizedTest
    @ValueSource(strings = { "CREATE", "create", "CREATE OR REPLACE" })
    void testRunImprover(final String udfCreateCommandStart)
            throws IOException, BucketAccessException, TimeoutException {
        when(this.bucket.getBucketName()).thenReturn("default");
        when(this.bucket.getBucketFsName()).thenReturn("bfsdefault");
        final Path dir = this.tempDir.resolve("buckets/bfsdefault/default/");
        Files.createDirectories(dir);
        Files.writeString(dir.resolve("classes.lst"), "java/lang/Object\n");
        final String result = new UdfStartUpTimeImproverInt(this.tempDir.toString()).run(
                udfCreateCommandStart + UDF_DEF_PART_2, "/buckets/bfsdefault/default/classes.lst", this.bucket,
                "my-dump.jsa");
        assertThat(result, equalTo(
                "CREATE OR REPLACE JAVA SCALAR SCRIPT \"MY_UDF\" (...) RETURNS VARCHAR(50) UTF8 AS\n%scriptclass com.exasol.testudf.MyUdf;\n%jvmoption -Xms128m -Xmx1024m -Xss512k -XX:SharedArchiveFile=/buckets/bfsdefault/default/my-dump.jsa;\n%jar /buckets/bfsdefault/default/udf-for-testing.jar;\n\n"));
        verify(this.bucket).uploadFileNonBlocking(Path.of("/tmp/dump.jsa"), "my-dump.jsa");
    }

    @Test
    void testClassListDoesNotExist() {
        final UdfStartUpTimeImproverInt improver = new UdfStartUpTimeImproverInt(this.tempDir.toString());
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> improver.run(UDF_DEF, "/buckets/bfsdefault/default/classes.lst", this.bucket, "my-dump.jsa"));
        assertThat(exception.getMessage(), Matchers.startsWith("E-USTI-8: Could not find the specified classes list"));
    }
}