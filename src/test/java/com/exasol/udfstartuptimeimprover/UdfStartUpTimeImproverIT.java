package com.exasol.udfstartuptimeimprover;

import static com.exasol.udfstartuptimeimprover.UdfStartUpTimeImprover.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.*;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exasol.bucketfs.Bucket;
import com.exasol.bucketfs.BucketAccessException;
import com.exasol.containers.ExasolContainer;
import com.exasol.dbbuilder.dialects.exasol.*;
import com.exasol.dbbuilder.dialects.exasol.udf.UdfScript;
import com.exasol.mavenprojectversiongetter.MavenProjectVersionGetter;
import com.exasol.udfdebugging.UdfTestSetup;

@Testcontainers
class UdfStartUpTimeImproverIT {
    @Container
    private static final ExasolContainer<? extends ExasolContainer<?>> EXASOL = new ExasolContainer<>().withReuse(true);
    private static final String CURRENT_VERSION = MavenProjectVersionGetter.getCurrentProjectVersion();
    private static final String BUCKET_FS_CONNECTION = "MY_BUCKET_FS_CONNECTION";
    private static Connection connection;
    private static Statement statement;
    private static Bucket bucket;
    private static ExasolSchema schema;

    @BeforeAll
    static void beforeAll()
            throws SQLException, BucketAccessException, IOException, TimeoutException, InterruptedException {
        EXASOL.getDefaultBucket().uploadFile(Path.of("udf-for-testing/target/udf-for-testing.jar"),
                "udf-for-testing.jar");
        EXASOL.getDefaultBucket().uploadFile(
                Path.of("target/java-udf-startup-time-improver" + "-" + CURRENT_VERSION + ".jar"), "improver.jar");
        connection = EXASOL.createConnection();
        statement = connection.createStatement();
        final UdfTestSetup udfTestSetup = new UdfTestSetup(EXASOL.getHostIp(), EXASOL.getDefaultBucket(), connection);
        final ExasolObjectFactory objectFactory = new ExasolObjectFactory(connection,
                ExasolObjectConfiguration.builder().withJvmOptions(udfTestSetup.getJvmOptions()).build());
        schema = objectFactory.createSchema("TEST");
        schema.createUdfBuilder("MY_UDF").inputType(UdfScript.InputType.SCALAR).language(UdfScript.Language.JAVA)
                .returns("VARCHAR(50) UTF8")
                .bucketFsContent("com.exasol.testudf.MyUdf", "/buckets/bfsdefault/default/udf-for-testing.jar").build();
        schema.createUdfBuilder("JAVA_UDF_STARTUP_TIME_IMPROVER_INT")
                .parameter(PARAMETER_UDF_DEFINITION, "VARCHAR(2000000) UTF8")
                .parameter(PARAMETER_CLASSES, "VARCHAR(2000) UTF8").parameter(PARAMETER_CONNECTION, "VARCHAR(200) UTF8")
                .parameter(PARAMETER_BUCKET_FS_PORT, "INTEGER")
                .parameter(PARAMETER_BUCKET_FS_SERVICE, "VARCHAR(200) UTF8")
                .parameter(PARAMETER_BUCKET_FS_BUCKET, "VARCHAR(200) UTF8").inputType(UdfScript.InputType.SCALAR)
                .parameter(PARAMETER_PATH_FOR_DUMP, "VARCHAR(200) UTF8").language(UdfScript.Language.JAVA)
                .returns("VARCHAR(2000) UTF8")
                .bucketFsContent("com.exasol.udfstartuptimeimprover.UdfStartUpTimeImprover",
                        "/buckets/bfsdefault/default/improver.jar")
                .build();

        schema.createScriptBuilder("JAVA_UDF_STARTUP_TIME_IMPROVER").parameter("UDF_SCHEMA", "UDF_NAME", "CLASSES",
                "CONNECTION_NAME", "BUCKET_FS_PORT", "BUCKET_FS_SERVICE", "BUCKET_FS_BUCKET", "PATH_FOR_DUMP")
                .content(getImproverUdf()).build();

        objectFactory.createConnectionDefinition(BUCKET_FS_CONNECTION, "", "",
                EXASOL.getDefaultBucket().getWritePassword());
        bucket = EXASOL.getDefaultBucket();
        bucket.uploadStringContent(
                "java/lang/Object\njava/io/Serializable\njava/lang/Comparable\njava/lang/CharSequence", "classes.lst");
    }

    @NotNull
    private static String getImproverUdf() throws IOException {
        try (final InputStream inputStream = UdfStartUpTimeImprover.class.getClassLoader()
                .getResourceAsStream("udf.lua")) {
            return new String(Objects.requireNonNull(inputStream).readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    @AfterAll
    static void afterAll() throws SQLException {
        statement.close();
        connection.close();
    }

    @BeforeEach
    void setUp() throws BucketAccessException, InterruptedException, SQLException {
        statement.executeUpdate("DROP SCRIPT IF EXISTS TEST.MY_UDF;");
        schema.createUdfBuilder("MY_UDF").inputType(UdfScript.InputType.SCALAR).language(UdfScript.Language.JAVA)
                .returns("VARCHAR(50) UTF8")
                .bucketFsContent("com.exasol.testudf.MyUdf", "/buckets/bfsdefault/default/udf-for-testing.jar").build();
    }

    @SuppressWarnings("java:S2925") // sleep can be removed after https://github.com/exasol/bucketfs-java/issues/42 is
    // fixed
    private void cleanupBucket() throws InterruptedException, BucketAccessException {
        bucket.deleteFileNonBlocking("my-dump.jsa");
        Thread.sleep(10_000);// give BucketFS time to sync
    }

    @Test
    void testRawImprover(@TempDir final Path tempDir) throws SQLException, InterruptedException, BucketAccessException {
        cleanupBucket();
        try (final ResultSet resultSet = statement.executeQuery(
                "SELECT TEST.JAVA_UDF_STARTUP_TIME_IMPROVER_INT( (SELECT SCRIPT_TEXT FROM SYS.EXA_ALL_SCRIPTS WHERE SCRIPT_NAME='MY_UDF' AND SCRIPT_SCHEMA='TEST'), '/buckets/bfsdefault/default/classes.lst', '"
                        + BUCKET_FS_CONNECTION + "', 2580, '" + bucket.getBucketFsName() + "', '"
                        + bucket.getBucketName() + "', 'my-dump.jsa');")) {
            resultSet.next();
            final String rewrittenCreate = resultSet.getString(1);
            assertAll(//
                    () -> assertThat(rewrittenCreate, startsWith(
                            "CREATE OR REPLACE JAVA SCALAR SCRIPT \"MY_UDF\" (...) RETURNS VARCHAR(50) UTF8 AS")),
                    () -> assertThat(rewrittenCreate,
                            containsString("-XX:SharedArchiveFile=/buckets/bfsdefault/default/my-dump.jsa;")),
                    () -> assertDoesNotThrow(() -> bucket.downloadFile("my-dump.jsa", tempDir.resolve("my-dump.jsa"))), //
                    () -> assertOptimizedUdfStillWorks(rewrittenCreate)//
            );
        }
    }

    @Test
    void testMissingStingParameter() {
        final String query = "SELECT TEST.JAVA_UDF_STARTUP_TIME_IMPROVER_INT( NULL, '/buckets/bfsdefault/default/classes.lst', '"
                + BUCKET_FS_CONNECTION + "', 2580, '" + bucket.getBucketFsName() + "', '" + bucket.getBucketName()
                + "', 'my-dump.jsa');";
        final SQLDataException exception = assertThrows(SQLDataException.class, () -> statement.executeQuery(query));
        assertThat(exception.getMessage(), containsString(
                "E-USTI-12: Could not read required parameter 'UDF_DEFINITION'. Make sure that you provide that parameter and that it's of type VARCHAR."));
    }

    @Test
    void testMissingIntParameter() {
        final String query = "SELECT TEST.JAVA_UDF_STARTUP_TIME_IMPROVER_INT( (SELECT SCRIPT_TEXT FROM SYS.EXA_ALL_SCRIPTS WHERE SCRIPT_NAME='MY_UDF' AND SCRIPT_SCHEMA='TEST'), '/buckets/bfsdefault/default/classes.lst', '"
                + BUCKET_FS_CONNECTION + "', NULL, '" + bucket.getBucketFsName() + "', '" + bucket.getBucketName()
                + "', 'my-dump.jsa');";
        final SQLDataException exception = assertThrows(SQLDataException.class, () -> statement.executeQuery(query));
        assertThat(exception.getMessage(), containsString(
                "E-USTI-15: Could not read required parameter 'BUCKET_FS_PORT'. Make sure that you provide that parameter and that it's of type INTEGER."));
    }

    @Test
    void testImproverWithLuaWrapper() throws SQLException, InterruptedException, BucketAccessException {
        cleanupBucket();
        statement.executeUpdate(
                "execute script TEST.JAVA_UDF_STARTUP_TIME_IMPROVER('TEST', 'MY_UDF', '/buckets/bfsdefault/default/classes.lst', '"
                        + BUCKET_FS_CONNECTION + "', 2580, '" + bucket.getBucketFsName() + "', '"
                        + bucket.getBucketName() + "', 'my-dump.jsa');");
        assertMyUdfWorks();
    }

    private void assertOptimizedUdfStillWorks(final String rewrittenCreate) throws SQLException {
        statement.executeUpdate(rewrittenCreate + "%jvmoption -Xshare:on;\n");
        assertMyUdfWorks();
    }

    private void assertMyUdfWorks() throws SQLException {
        try (final ResultSet resultSet = statement.executeQuery("SELECT TEST.MY_UDF();")) {
            resultSet.next();
            assertThat(resultSet.getString(1), equalTo("Hello World"));
        }
    }
}
