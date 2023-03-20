<!-- @formatter:off -->
# Dependencies

## Java udf Startup Time Improver

### Compile Dependencies

| Dependency                   | License                |
| ---------------------------- | ---------------------- |
| [Exasol UDF API for Java][0] | [MIT License][1]       |
| [EXASolution JDBC Driver][2] | [EXAClient License][3] |
| [error-reporting-java][4]    | [MIT License][5]       |
| [BucketFS Java][6]           | [MIT License][7]       |

### Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [JUnit Jupiter API][8]                          | [Eclipse Public License v2.0][9]  |
| [JUnit Jupiter Params][8]                       | [Eclipse Public License v2.0][9]  |
| [Hamcrest][10]                                  | [BSD License 3][11]               |
| [EqualsVerifier | release normal jar][12]       | [Apache License, Version 2.0][13] |
| [Testcontainers :: JUnit Jupiter Extension][14] | [MIT][15]                         |
| [Test containers for Exasol on Docker][16]      | [MIT License][17]                 |
| [Test Database Builder for Java][18]            | [MIT License][19]                 |
| [Maven Project Version Getter][20]              | [MIT License][21]                 |
| [udf-debugging-java][22]                        | [MIT License][23]                 |
| [mockito-junit-jupiter][24]                     | [The MIT License][25]             |
| [JaCoCo :: Agent][26]                           | [Eclipse Public License 2.0][27]  |

### Plugin Dependencies

| Dependency                                              | License                           |
| ------------------------------------------------------- | --------------------------------- |
| [SonarQube Scanner for Maven][28]                       | [GNU LGPL 3][29]                  |
| [Apache Maven Compiler Plugin][30]                      | [Apache License, Version 2.0][13] |
| [Apache Maven Enforcer Plugin][31]                      | [Apache-2.0][13]                  |
| [Maven Flatten Plugin][32]                              | [Apache Software Licenese][13]    |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][33] | [ASL2][34]                        |
| [Maven Surefire Plugin][35]                             | [Apache License, Version 2.0][13] |
| [Versions Maven Plugin][36]                             | [Apache License, Version 2.0][13] |
| [Apache Maven Assembly Plugin][37]                      | [Apache License, Version 2.0][13] |
| [Apache Maven JAR Plugin][38]                           | [Apache License, Version 2.0][13] |
| [Artifact reference checker and unifier][39]            | [MIT License][40]                 |
| [Apache Maven Dependency Plugin][41]                    | [Apache License, Version 2.0][13] |
| [Project keeper maven plugin][42]                       | [The MIT License][43]             |
| [Exec Maven Plugin][44]                                 | [Apache License 2][13]            |
| [Maven Failsafe Plugin][45]                             | [Apache License, Version 2.0][13] |
| [JaCoCo :: Maven Plugin][46]                            | [Eclipse Public License 2.0][27]  |
| [error-code-crawler-maven-plugin][47]                   | [MIT License][48]                 |
| [Reproducible Build Maven Plugin][49]                   | [Apache 2.0][34]                  |
| [Apache Maven Clean Plugin][50]                         | [Apache License, Version 2.0][13] |
| [Apache Maven Resources Plugin][51]                     | [Apache License, Version 2.0][13] |
| [Apache Maven Install Plugin][52]                       | [Apache License, Version 2.0][13] |
| [Apache Maven Deploy Plugin][53]                        | [Apache License, Version 2.0][13] |
| [Apache Maven Site Plugin][54]                          | [Apache License, Version 2.0][13] |

## Udf for Testing

### Compile Dependencies

| Dependency                   | License          |
| ---------------------------- | ---------------- |
| [Exasol UDF API for Java][0] | [MIT License][1] |

### Plugin Dependencies

| Dependency                                              | License                           |
| ------------------------------------------------------- | --------------------------------- |
| [SonarQube Scanner for Maven][28]                       | [GNU LGPL 3][29]                  |
| [Apache Maven Compiler Plugin][30]                      | [Apache-2.0][13]                  |
| [Apache Maven Enforcer Plugin][31]                      | [Apache-2.0][13]                  |
| [Maven Flatten Plugin][32]                              | [Apache Software Licenese][13]    |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][33] | [ASL2][34]                        |
| [Maven Surefire Plugin][35]                             | [Apache License, Version 2.0][13] |
| [Versions Maven Plugin][36]                             | [Apache License, Version 2.0][13] |
| [Apache Maven Assembly Plugin][37]                      | [Apache License, Version 2.0][13] |
| [Apache Maven JAR Plugin][38]                           | [Apache License, Version 2.0][13] |
| [Artifact reference checker and unifier][39]            | [MIT License][40]                 |
| [JaCoCo :: Maven Plugin][46]                            | [Eclipse Public License 2.0][27]  |
| [error-code-crawler-maven-plugin][47]                   | [MIT License][48]                 |
| [Reproducible Build Maven Plugin][49]                   | [Apache 2.0][34]                  |
| [Apache Maven Clean Plugin][50]                         | [Apache License, Version 2.0][13] |
| [Apache Maven Resources Plugin][51]                     | [Apache License, Version 2.0][13] |
| [Apache Maven Install Plugin][52]                       | [Apache License, Version 2.0][13] |
| [Apache Maven Deploy Plugin][53]                        | [Apache License, Version 2.0][13] |
| [Apache Maven Site Plugin][54]                          | [Apache License, Version 2.0][13] |

[0]: https://github.com/exasol/udf-api-java/
[1]: https://github.com/exasol/udf-api-java/blob/main/LICENSE
[2]: http://www.exasol.com
[3]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
[4]: https://github.com/exasol/error-reporting-java/
[5]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[6]: https://github.com/exasol/bucketfs-java/
[7]: https://github.com/exasol/bucketfs-java/blob/main/LICENSE
[8]: https://junit.org/junit5/
[9]: https://www.eclipse.org/legal/epl-v20.html
[10]: http://hamcrest.org/JavaHamcrest/
[11]: http://opensource.org/licenses/BSD-3-Clause
[12]: https://www.jqno.nl/equalsverifier
[13]: https://www.apache.org/licenses/LICENSE-2.0.txt
[14]: https://testcontainers.org
[15]: http://opensource.org/licenses/MIT
[16]: https://github.com/exasol/exasol-testcontainers/
[17]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[18]: https://github.com/exasol/test-db-builder-java/
[19]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[20]: https://github.com/exasol/maven-project-version-getter/
[21]: https://github.com/exasol/maven-project-version-getter/blob/main/LICENSE
[22]: https://github.com/exasol/udf-debugging-java/
[23]: https://github.com/exasol/udf-debugging-java/blob/main/LICENSE
[24]: https://github.com/mockito/mockito
[25]: https://github.com/mockito/mockito/blob/main/LICENSE
[26]: https://www.eclemma.org/jacoco/index.html
[27]: https://www.eclipse.org/legal/epl-2.0/
[28]: http://sonarsource.github.io/sonar-scanner-maven/
[29]: http://www.gnu.org/licenses/lgpl.txt
[30]: https://maven.apache.org/plugins/maven-compiler-plugin/
[31]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[32]: https://www.mojohaus.org/flatten-maven-plugin/
[33]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[34]: http://www.apache.org/licenses/LICENSE-2.0.txt
[35]: https://maven.apache.org/surefire/maven-surefire-plugin/
[36]: https://www.mojohaus.org/versions/versions-maven-plugin/
[37]: https://maven.apache.org/plugins/maven-assembly-plugin/
[38]: https://maven.apache.org/plugins/maven-jar-plugin/
[39]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[40]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[41]: https://maven.apache.org/plugins/maven-dependency-plugin/
[42]: https://github.com/exasol/project-keeper/
[43]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[44]: https://www.mojohaus.org/exec-maven-plugin
[45]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[46]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[47]: https://github.com/exasol/error-code-crawler-maven-plugin/
[48]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[49]: http://zlika.github.io/reproducible-build-maven-plugin
[50]: https://maven.apache.org/plugins/maven-clean-plugin/
[51]: https://maven.apache.org/plugins/maven-resources-plugin/
[52]: https://maven.apache.org/plugins/maven-install-plugin/
[53]: https://maven.apache.org/plugins/maven-deploy-plugin/
[54]: https://maven.apache.org/plugins/maven-site-plugin/
