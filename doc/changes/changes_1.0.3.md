# Java udf Startup Time Improver 1.0.3, released 2023-09-27

Code name: Fix CVE-2023-42503

## Summary

This release fixes CVE-2023-42503 in test dependency `org.apache.commons:commons-compress`

## Security

* #17: Fixed CVE-2023-42503 in `org.apache.commons:commons-compress`

## Documentation

* #15: Fixed broken links

## Dependency Updates

### Java UDF Startup Time Improver

#### Compile Dependency Updates

* Updated `com.exasol:bucketfs-java:3.0.0` to `3.1.1`
* Updated `com.exasol:exasol-jdbc:7.1.17` to `7.1.20`

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:6.5.1` to `6.6.2`
* Updated `com.exasol:test-db-builder-java:3.4.2` to `3.5.1`
* Updated `com.exasol:udf-debugging-java:0.6.8` to `0.6.11`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.14.1` to `3.15.2`
* Updated `org.jacoco:org.jacoco.agent:0.8.8` to `0.8.10`
* Updated `org.junit.jupiter:junit-jupiter-api:5.9.2` to `5.10.0`
* Updated `org.junit.jupiter:junit-jupiter-params:5.9.2` to `5.10.0`
* Updated `org.mockito:mockito-junit-jupiter:5.2.0` to `5.5.0`
* Updated `org.slf4j:slf4j-jdk14:2.0.7` to `2.0.9`
* Updated `org.testcontainers:junit-jupiter:1.17.6` to `1.19.0`

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.2.2` to `1.3.0`
* Updated `com.exasol:project-keeper-maven-plugin:2.9.4` to `2.9.12`
* Updated `org.apache.maven.plugins:maven-assembly-plugin:3.5.0` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.10.1` to `3.11.0`
* Updated `org.apache.maven.plugins:maven-dependency-plugin:3.5.0` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.2.1` to `3.4.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M8` to `3.1.2`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M8` to `3.1.2`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:2.0.1`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.3.0` to `1.5.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.14.2` to `2.16.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.8` to `0.8.10`

### UDF for Testing

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.2.2` to `1.3.0`
* Updated `org.apache.maven.plugins:maven-assembly-plugin:3.5.0` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.2.1` to `3.4.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M8` to `3.1.2`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:2.0.1`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.3.0` to `1.5.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.14.2` to `2.16.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.8` to `0.8.10`
