# Java udf Startup Time Improver 1.0.4, released 2024-03-11

Code name: Fix CVE-2024-25710 and CVE-2024-26308 in test dependency `org.apache.commons:commons-compress`

## Summary

This release fixes vulnerabilities CVE-2024-25710 and CVE-2024-26308 in test dependency `org.apache.commons:commons-compress`.

Starting with this release we test the project with both Exasol 7.1 and 8.

## Security

* #19: Fixed vulnerabilities CVE-2024-25710 and CVE-2024-26308 in test dependency `org.apache.commons:commons-compress`

## Dependency Updates

### Java UDF Startup Time Improver

#### Compile Dependency Updates

* Updated `com.exasol:bucketfs-java:3.1.1` to `3.1.2`

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:6.6.2` to `7.0.1`
* Updated `com.exasol:test-db-builder-java:3.5.1` to `3.5.3`
* Updated `com.exasol:udf-debugging-java:0.6.11` to `0.6.12`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.15.2` to `3.15.8`
* Updated `org.jacoco:org.jacoco.agent:0.8.10` to `0.8.11`
* Updated `org.junit.jupiter:junit-jupiter-api:5.10.0` to `5.10.2`
* Updated `org.junit.jupiter:junit-jupiter-params:5.10.0` to `5.10.2`
* Updated `org.mockito:mockito-junit-jupiter:5.5.0` to `5.11.0`
* Updated `org.slf4j:slf4j-jdk14:2.0.9` to `2.0.12`
* Updated `org.testcontainers:junit-jupiter:1.19.0` to `1.19.7`

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.3.0` to `2.0.0`
* Updated `com.exasol:project-keeper-maven-plugin:2.9.12` to `4.1.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.11.0` to `3.12.1`
* Updated `org.apache.maven.plugins:maven-dependency-plugin:3.6.0` to `3.6.1`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.4.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.1.2` to `3.2.5`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.1.2` to `3.2.5`
* Added `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.5.0` to `1.6.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.16.0` to `2.16.2`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.10` to `0.8.11`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184` to `3.10.0.2594`

### UDF for Testing

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.3.0` to `2.0.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.4.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.1.2` to `3.2.5`
* Added `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.5.0` to `1.6.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.16.0` to `2.16.2`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.10` to `0.8.11`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184` to `3.10.0.2594`
