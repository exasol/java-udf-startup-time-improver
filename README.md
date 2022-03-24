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

The Java UDF startup time improver is a tool that optimizes Java UDF. It does so by preloading the Java classes and writing them into a class dump on BucketFS. Then it replaces the script definition with a different one that declares a JVM-option which instructs the JVM to use the dump.

**This tool can only optimize projects that are prepared for it.** To find out if a certain project is already prepared for being optimized look out for the badge "Prepared for Java UDF startup time improver" in the project's readme.

This tool only has an impact when used with Exasol version 7.0.17 and 7.1.7. On older version it has no effect.

## Installation

1. Download the latest release of this project.
2. Upload the jar to BucketFS.
3. Run the [installation script](src/install.sql)
   The script contains the path `/buckets/bfsdefault/default/java-udf-startup-time-improver.jar`. Please adapt it to point to the correct Bucket and jar.

## Usage

Create a connection with the write-password of the BucketFS where the improver can write the class-dump to.

```sql
CREATE CONNECTION BFS_CONNECTION
TO ''
USER ''
IDENTIFIED BY '<BucketFsWritePassword>';
```

Download the class list for the UDF / adapter script that you want to optimize. You can find file `classes.lst` attached to the GitHub release. Now upload the file to a bucket in BucketFS.

Run the improver:

```sql
execute script TEST.JAVA_UDF_STARTUP_TIME_IMPROVER(
    '<schema of the script to optimize>', 
    '<name of the script to optimize>',  
    '<name of the connection we just created>', 
    <bucketfs-port>, 
    '<name of the BucketFS service where the improver stores the class-dump>', 
    '<name of the Bucket where the improver stores the class-dump>', 
    '<path for the class dump file in the bucket>')
```

For example, if you want to optimize the udf `TEST.MY_UDF` you can use parameters like:

```sql
execute script TEST.JAVA_UDF_STARTUP_TIME_IMPROVER(
    'TEST', 
    'MY_UDF', 
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
