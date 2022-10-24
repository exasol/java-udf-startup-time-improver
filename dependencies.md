<!-- @formatter:off -->
# Dependencies

## Java udf Startup Time Improver

### Compile Dependencies

| Dependency                             | License                |
| -------------------------------------- | ---------------------- |
| [Java Interface for EXASOL Scripts][0] | [MIT License][1]       |
| [EXASolution JDBC Driver][0]           | [EXAClient License][2] |
| [error-reporting-java][3]              | [MIT License][4]       |
| [BucketFS Java][5]                     | [MIT License][6]       |

### Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [JUnit Jupiter API][7]                          | [Eclipse Public License v2.0][8]  |
| [JUnit Jupiter Params][7]                       | [Eclipse Public License v2.0][8]  |
| [Hamcrest][9]                                   | [BSD License 3][10]               |
| [EqualsVerifier | release normal jar][11]       | [Apache License, Version 2.0][12] |
| [Testcontainers :: JUnit Jupiter Extension][13] | [MIT][14]                         |
| [Test containers for Exasol on Docker][15]      | [MIT License][16]                 |
| [Test Database Builder for Java][17]            | [MIT License][18]                 |
| [Maven Project Version Getter][19]              | [MIT License][20]                 |
| [udf-debugging-java][21]                        | [MIT][22]                         |
| [mockito-junit-jupiter][23]                     | [The MIT License][24]             |
| [JaCoCo :: Agent][25]                           | [Eclipse Public License 2.0][26]  |

### Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][27]                       | [GNU LGPL 3][28]                               |
| [Apache Maven Compiler Plugin][29]                      | [Apache License, Version 2.0][12]              |
| [Apache Maven Enforcer Plugin][30]                      | [Apache License, Version 2.0][12]              |
| [Maven Flatten Plugin][31]                              | [Apache Software Licenese][32]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][33] | [ASL2][32]                                     |
| [Maven Surefire Plugin][34]                             | [Apache License, Version 2.0][12]              |
| [Versions Maven Plugin][35]                             | [Apache License, Version 2.0][12]              |
| [Apache Maven Assembly Plugin][36]                      | [Apache License, Version 2.0][12]              |
| [Apache Maven JAR Plugin][37]                           | [Apache License, Version 2.0][12]              |
| [Artifact reference checker and unifier][38]            | [MIT][22]                                      |
| [Apache Maven Dependency Plugin][39]                    | [Apache License, Version 2.0][12]              |
| [Project keeper maven plugin][40]                       | [The MIT License][41]                          |
| [Exec Maven Plugin][42]                                 | [Apache License 2][32]                         |
| [Maven Failsafe Plugin][43]                             | [Apache License, Version 2.0][12]              |
| [JaCoCo :: Maven Plugin][44]                            | [Eclipse Public License 2.0][26]               |
| [error-code-crawler-maven-plugin][45]                   | [MIT License][46]                              |
| [Reproducible Build Maven Plugin][47]                   | [Apache 2.0][32]                               |
| [Maven Clean Plugin][48]                                | [The Apache Software License, Version 2.0][32] |
| [Maven Resources Plugin][49]                            | [The Apache Software License, Version 2.0][32] |
| [Maven Install Plugin][50]                              | [The Apache Software License, Version 2.0][32] |
| [Maven Deploy Plugin][51]                               | [The Apache Software License, Version 2.0][32] |
| [Maven Site Plugin 3][52]                               | [The Apache Software License, Version 2.0][32] |

## Udf for Testing

### Compile Dependencies

| Dependency                             | License          |
| -------------------------------------- | ---------------- |
| [Java Interface for EXASOL Scripts][0] | [MIT License][1] |

### Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][27]                       | [GNU LGPL 3][28]                               |
| [Apache Maven Compiler Plugin][29]                      | [Apache License, Version 2.0][12]              |
| [Apache Maven Enforcer Plugin][30]                      | [Apache License, Version 2.0][12]              |
| [Maven Flatten Plugin][31]                              | [Apache Software Licenese][32]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][33] | [ASL2][32]                                     |
| [Maven Surefire Plugin][34]                             | [Apache License, Version 2.0][12]              |
| [Versions Maven Plugin][35]                             | [Apache License, Version 2.0][12]              |
| [Apache Maven Assembly Plugin][36]                      | [Apache License, Version 2.0][12]              |
| [Apache Maven JAR Plugin][37]                           | [Apache License, Version 2.0][12]              |
| [Artifact reference checker and unifier][38]            | [MIT][22]                                      |
| [JaCoCo :: Maven Plugin][44]                            | [Eclipse Public License 2.0][26]               |
| [error-code-crawler-maven-plugin][45]                   | [MIT License][46]                              |
| [Reproducible Build Maven Plugin][47]                   | [Apache 2.0][32]                               |
| [Maven Clean Plugin][48]                                | [The Apache Software License, Version 2.0][32] |
| [Maven Resources Plugin][49]                            | [The Apache Software License, Version 2.0][32] |
| [Maven Install Plugin][50]                              | [The Apache Software License, Version 2.0][32] |
| [Maven Deploy Plugin][51]                               | [The Apache Software License, Version 2.0][32] |
| [Maven Site Plugin 3][52]                               | [The Apache Software License, Version 2.0][32] |

[0]: http://www.exasol.com
[1]: https://mit-license.org/
[2]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
[3]: https://github.com/exasol/error-reporting-java/
[4]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[5]: https://github.com/exasol/bucketfs-java/
[6]: https://github.com/exasol/bucketfs-java/blob/main/LICENSE
[7]: https://junit.org/junit5/
[8]: https://www.eclipse.org/legal/epl-v20.html
[9]: http://hamcrest.org/JavaHamcrest/
[10]: http://opensource.org/licenses/BSD-3-Clause
[11]: https://www.jqno.nl/equalsverifier
[12]: https://www.apache.org/licenses/LICENSE-2.0.txt
[13]: https://testcontainers.org
[14]: http://opensource.org/licenses/MIT
[15]: https://github.com/exasol/exasol-testcontainers/
[16]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[17]: https://github.com/exasol/test-db-builder-java/
[18]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[19]: https://github.com/exasol/maven-project-version-getter/
[20]: https://github.com/exasol/maven-project-version-getter/blob/main/LICENSE
[21]: https://github.com/exasol/udf-debugging-java/
[22]: https://opensource.org/licenses/MIT
[23]: https://github.com/mockito/mockito
[24]: https://github.com/mockito/mockito/blob/main/LICENSE
[25]: https://www.eclemma.org/jacoco/index.html
[26]: https://www.eclipse.org/legal/epl-2.0/
[27]: http://sonarsource.github.io/sonar-scanner-maven/
[28]: http://www.gnu.org/licenses/lgpl.txt
[29]: https://maven.apache.org/plugins/maven-compiler-plugin/
[30]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[31]: https://www.mojohaus.org/flatten-maven-plugin/
[32]: http://www.apache.org/licenses/LICENSE-2.0.txt
[33]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[34]: https://maven.apache.org/surefire/maven-surefire-plugin/
[35]: http://www.mojohaus.org/versions-maven-plugin/
[36]: https://maven.apache.org/plugins/maven-assembly-plugin/
[37]: https://maven.apache.org/plugins/maven-jar-plugin/
[38]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[39]: https://maven.apache.org/plugins/maven-dependency-plugin/
[40]: https://github.com/exasol/project-keeper/
[41]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[42]: http://www.mojohaus.org/exec-maven-plugin
[43]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[44]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[45]: https://github.com/exasol/error-code-crawler-maven-plugin/
[46]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[47]: http://zlika.github.io/reproducible-build-maven-plugin
[48]: http://maven.apache.org/plugins/maven-clean-plugin/
[49]: http://maven.apache.org/plugins/maven-resources-plugin/
[50]: http://maven.apache.org/plugins/maven-install-plugin/
[51]: http://maven.apache.org/plugins/maven-deploy-plugin/
[52]: http://maven.apache.org/plugins/maven-site-plugin/
