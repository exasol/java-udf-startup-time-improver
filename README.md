# Java UDF Startup Time Improver

[![Build Status](https://github.com/exasol/java-udf-startup-time-improver/actions/workflows/ci-build.yml/badge.svg)](https://github.com/exasol/java-udf-startup-time-improver/actions/workflows/ci-build.yml)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Ajava-udf-startup-time-improver&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.exasol%3Ajava-udf-startup-time-improver)

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Ajava-udf-startup-time-improver&metric=security_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Ajava-udf-startup-time-improver)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Ajava-udf-startup-time-improver&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Ajava-udf-startup-time-improver)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Ajava-udf-startup-time-improver&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Ajava-udf-startup-time-improver)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Ajava-udf-startup-time-improver&metric=sqale_index)](https://sonarcloud.io/dashboard?id=com.exasol%3Ajava-udf-startup-time-improver)

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Ajava-udf-startup-time-improver&metric=code_smells)](https://sonarcloud.io/dashboard?id=com.exasol%3Ajava-udf-startup-time-improver)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Ajava-udf-startup-time-improver&metric=coverage)](https://sonarcloud.io/dashboard?id=com.exasol%3Ajava-udf-startup-time-improver)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Ajava-udf-startup-time-improver&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=com.exasol%3Ajava-udf-startup-time-improver)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Ajava-udf-startup-time-improver&metric=ncloc)](https://sonarcloud.io/dashboard?id=com.exasol%3Ajava-udf-startup-time-improver)

The Java UDF startup time improver is a tool that optimizes Java UDF. It does so by preloading the Java classes and writing them into a class dump on BucketFS. Then it replaces the script definition with a different one that declares a jvm-option which introduces the JVM to use the dump.

## Installation

* Download the latest release of this project.
* Upload the jar to BucketFS.
* Create the following functions:
  ```sql
  CREATE JAVA SCALAR SCRIPT "JAVA_UDF_STARTUP_TIME_IMPROVER" (
    "UDF_DEFINITION" VARCHAR(2000000) UTF8, 
    "CLASSES" VARCHAR(2000) UTF8, 
    "CONNECTION_NAME" VARCHAR(200) UTF8, 
    "BUCKET_FS_PORT" DECIMAL(18,0), 
    "BUCKET_FS_SERVICE" VARCHAR(200) UTF8, 
    "BUCKET_FS_BUCKET" VARCHAR(200) UTF8, 
    "PATH_FOR_DUMP" VARCHAR(200) UTF8)
  RETURNS VARCHAR(2000) UTF8 AS
  %scriptclass com.exasol.udfstartuptimeimprover.UdfStartUpTimeImprover;
  %jar /buckets/bfsdefault/default/improver.jar;
  /
  
  CREATE LUA SCRIPT "JAVA_UDF_STARTUP_TIME_IMPROVER" (UDF_SCHEMA,UDF_NAME,CLASSES,CONNECTION_NAME,BUCKET_FS_PORT,BUCKET_FS_SERVICE,BUCKET_FS_BUCKET,PATH_FOR_DUMP) RETURNS ROWCOUNT AS
    local updatedUdfDef = query([[SELECT TEST.JAVA_UDF_STARTUP_TIME_IMPROVER_INT(
      (SELECT SCRIPT_TEXT FROM SYS.EXA_ALL_SCRIPTS WHERE SCRIPT_NAME=:udfName AND SCRIPT_SCHEMA=:udfSchema),:classes,:connection, :bfsPort, :bfsService, :bfsBucket, :pathForDump) AS CMD]], { udfName = UDF_NAME, udfSchema = UDF_SCHEMA, classes = CLASSES, connection = CONNECTION_NAME, bfsPort = BUCKET_FS_PORT, bfsService = BUCKET_FS_SERVICE, bfsBucket = BUCKET_FS_BUCKET, pathForDump = PATH_FOR_DUMP })
    query(updatedUdfDef[1].CMD)
  /
  ```

## Usage

Crate a connection with the write-password of the BucketFS where the improver can write the class-dump to.

```sql
CREATE CONNECTION BFS_CONNECTION
TO ''
USER ''
IDENTIFIED BY '<BucketFsWritePassword>';
```

Download the class list for the UDF / adapter script that you want to optimize. You can find this file attached to the GitHub release. Now upload the file to a bucket in BucketFS.

Run the improver:

```sql
execute script TEST.JAVA_UDF_STARTUP_TIME_IMPROVER(
    '<schema of the script to optimize>', 
    '<name of the script to optimize>', 
    '<path in bucketfs to the listt of classes to preload>', 
    '<name of the connection we just created>', 
    <bucketfs-port>, 
    '<name of the BucketFS service where the improver stors the class-dump>', 
    '<name of the Bucket where the improver stors the class-dump>', 
    '<path for the class.dump in the bucket>')
```

For example, if you want to optimize the udf `TEST.MY_UDF` you can use parameters like:

```sql
execute script TEST.JAVA_UDF_STARTUP_TIME_IMPROVER(
    'TEST', 
    'MY_UDF', 
    '/buckets/bfsdefault/default/classes.lst', 
    'BFS_CONNECTION', 
    2580, 
    'bfsdefault', 
    'default', 
    'my-dump.jsa')
```

When you execute the script, the improver creates the class-dump, uploads it to BucketFS and updates your script definition.

## Additional Information

* [Changelog](doc/changes/changelog.md)
* [Dependencies](dependencies.md)