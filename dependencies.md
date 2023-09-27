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
| [EqualsVerifier \| release normal jar][12]      | [Apache License, Version 2.0][13] |
| [Testcontainers :: JUnit Jupiter Extension][14] | [MIT][15]                         |
| [Test containers for Exasol on Docker][16]      | [MIT License][17]                 |
| [SLF4J JDK14 Provider][18]                      | [MIT License][19]                 |
| [Test Database Builder for Java][20]            | [MIT License][21]                 |
| [Maven Project Version Getter][22]              | [MIT License][23]                 |
| [udf-debugging-java][24]                        | [MIT License][25]                 |
| [mockito-junit-jupiter][26]                     | [The MIT License][27]             |
| [JaCoCo :: Agent][28]                           | [Eclipse Public License 2.0][29]  |

### Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][30]                       | [GNU LGPL 3][31]                               |
| [Apache Maven Compiler Plugin][32]                      | [Apache-2.0][13]                               |
| [Apache Maven Enforcer Plugin][33]                      | [Apache-2.0][13]                               |
| [Maven Flatten Plugin][34]                              | [Apache Software Licenese][13]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][35] | [ASL2][36]                                     |
| [Maven Surefire Plugin][37]                             | [Apache-2.0][13]                               |
| [Versions Maven Plugin][38]                             | [Apache License, Version 2.0][13]              |
| [duplicate-finder-maven-plugin Maven Mojo][39]          | [Apache License 2.0][40]                       |
| [Apache Maven Assembly Plugin][41]                      | [Apache-2.0][13]                               |
| [Apache Maven JAR Plugin][42]                           | [Apache License, Version 2.0][13]              |
| [Artifact reference checker and unifier][43]            | [MIT License][44]                              |
| [Apache Maven Dependency Plugin][45]                    | [Apache-2.0][13]                               |
| [Project keeper maven plugin][46]                       | [The MIT License][47]                          |
| [Exec Maven Plugin][48]                                 | [Apache License 2][13]                         |
| [Maven Failsafe Plugin][49]                             | [Apache-2.0][13]                               |
| [JaCoCo :: Maven Plugin][50]                            | [Eclipse Public License 2.0][29]               |
| [error-code-crawler-maven-plugin][51]                   | [MIT License][52]                              |
| [Reproducible Build Maven Plugin][53]                   | [Apache 2.0][36]                               |
| [Maven Clean Plugin][54]                                | [The Apache Software License, Version 2.0][36] |
| [Maven Resources Plugin][55]                            | [The Apache Software License, Version 2.0][36] |
| [Maven Install Plugin][56]                              | [The Apache Software License, Version 2.0][36] |
| [Maven Deploy Plugin][57]                               | [The Apache Software License, Version 2.0][36] |
| [Maven Site Plugin 3][58]                               | [The Apache Software License, Version 2.0][36] |

## Udf for Testing

### Compile Dependencies

| Dependency                   | License          |
| ---------------------------- | ---------------- |
| [Exasol UDF API for Java][0] | [MIT License][1] |

### Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][30]                       | [GNU LGPL 3][31]                               |
| [Apache Maven Compiler Plugin][32]                      | [Apache-2.0][13]                               |
| [Apache Maven Enforcer Plugin][33]                      | [Apache-2.0][13]                               |
| [Maven Flatten Plugin][34]                              | [Apache Software Licenese][13]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][35] | [ASL2][36]                                     |
| [Maven Surefire Plugin][37]                             | [Apache-2.0][13]                               |
| [Versions Maven Plugin][38]                             | [Apache License, Version 2.0][13]              |
| [duplicate-finder-maven-plugin Maven Mojo][39]          | [Apache License 2.0][40]                       |
| [Apache Maven Assembly Plugin][41]                      | [Apache-2.0][13]                               |
| [Apache Maven JAR Plugin][42]                           | [Apache License, Version 2.0][13]              |
| [Artifact reference checker and unifier][43]            | [MIT License][44]                              |
| [JaCoCo :: Maven Plugin][50]                            | [Eclipse Public License 2.0][29]               |
| [error-code-crawler-maven-plugin][51]                   | [MIT License][52]                              |
| [Reproducible Build Maven Plugin][53]                   | [Apache 2.0][36]                               |
| [Maven Clean Plugin][54]                                | [The Apache Software License, Version 2.0][36] |
| [Maven Resources Plugin][55]                            | [The Apache Software License, Version 2.0][36] |
| [Maven Install Plugin][56]                              | [The Apache Software License, Version 2.0][36] |
| [Maven Deploy Plugin][57]                               | [The Apache Software License, Version 2.0][36] |
| [Maven Site Plugin 3][58]                               | [The Apache Software License, Version 2.0][36] |

[0]: https://github.com/exasol/udf-api-java/
[1]: https://github.com/exasol/udf-api-java/blob/main/LICENSE
[2]: http://www.exasol.com
[3]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/7.1.20/exasol-jdbc-7.1.20-license.txt
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
[14]: https://java.testcontainers.org
[15]: http://opensource.org/licenses/MIT
[16]: https://github.com/exasol/exasol-testcontainers/
[17]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[18]: http://www.slf4j.org
[19]: http://www.opensource.org/licenses/mit-license.php
[20]: https://github.com/exasol/test-db-builder-java/
[21]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[22]: https://github.com/exasol/maven-project-version-getter/
[23]: https://github.com/exasol/maven-project-version-getter/blob/main/LICENSE
[24]: https://github.com/exasol/udf-debugging-java/
[25]: https://github.com/exasol/udf-debugging-java/blob/main/LICENSE
[26]: https://github.com/mockito/mockito
[27]: https://github.com/mockito/mockito/blob/main/LICENSE
[28]: https://www.eclemma.org/jacoco/index.html
[29]: https://www.eclipse.org/legal/epl-2.0/
[30]: http://sonarsource.github.io/sonar-scanner-maven/
[31]: http://www.gnu.org/licenses/lgpl.txt
[32]: https://maven.apache.org/plugins/maven-compiler-plugin/
[33]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[34]: https://www.mojohaus.org/flatten-maven-plugin/
[35]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[36]: http://www.apache.org/licenses/LICENSE-2.0.txt
[37]: https://maven.apache.org/surefire/maven-surefire-plugin/
[38]: https://www.mojohaus.org/versions/versions-maven-plugin/
[39]: https://basepom.github.io/duplicate-finder-maven-plugin
[40]: http://www.apache.org/licenses/LICENSE-2.0.html
[41]: https://maven.apache.org/plugins/maven-assembly-plugin/
[42]: https://maven.apache.org/plugins/maven-jar-plugin/
[43]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[44]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[45]: https://maven.apache.org/plugins/maven-dependency-plugin/
[46]: https://github.com/exasol/project-keeper/
[47]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[48]: https://www.mojohaus.org/exec-maven-plugin
[49]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[50]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[51]: https://github.com/exasol/error-code-crawler-maven-plugin/
[52]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[53]: http://zlika.github.io/reproducible-build-maven-plugin
[54]: http://maven.apache.org/plugins/maven-clean-plugin/
[55]: http://maven.apache.org/plugins/maven-resources-plugin/
[56]: http://maven.apache.org/plugins/maven-install-plugin/
[57]: http://maven.apache.org/plugins/maven-deploy-plugin/
[58]: http://maven.apache.org/plugins/maven-site-plugin/
