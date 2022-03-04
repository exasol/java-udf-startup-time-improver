<!-- @formatter:off -->
# Dependencies

## Java udf Startup Time Improver

### Compile Dependencies

| Dependency                             | License                |
| -------------------------------------- | ---------------------- |
| [Java Interface for EXASOL Scripts][0] | [MIT License][1]       |
| [EXASolution JDBC Driver][0]           | [EXAClient License][3] |
| [error-reporting-java][4]              | [MIT][5]               |
| [BucketFS Java][6]                     | [MIT][5]               |
| [Project Lombok][8]                    | [The MIT License][9]   |

### Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [JUnit Jupiter Engine][10]                      | [Eclipse Public License v2.0][11] |
| [JUnit Jupiter Params][10]                      | [Eclipse Public License v2.0][11] |
| [Hamcrest][14]                                  | [BSD License 3][15]               |
| [Testcontainers :: JUnit Jupiter Extension][16] | [MIT][17]                         |
| [Test containers for Exasol on Docker][18]      | [MIT][5]                          |
| [Test Database Builder for Java][20]            | [MIT][5]                          |
| [Maven Project Version Getter][22]              | [MIT][5]                          |
| [udf-debugging-java][24]                        | [MIT][5]                          |
| [mockito-junit-jupiter][26]                     | [The MIT License][27]             |
| [JaCoCo :: Agent][28]                           | [Eclipse Public License 2.0][29]  |

### Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [Apache Maven Enforcer Plugin][30]                      | [Apache License, Version 2.0][31]              |
| [Maven Flatten Plugin][32]                              | [Apache Software Licenese][33]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][34] | [ASL2][33]                                     |
| [Reproducible Build Maven Plugin][36]                   | [Apache 2.0][33]                               |
| [Maven Surefire Plugin][38]                             | [Apache License, Version 2.0][31]              |
| [Versions Maven Plugin][40]                             | [Apache License, Version 2.0][31]              |
| [Maven Compiler Plugin][42]                             | [The Apache Software License, Version 2.0][33] |
| [Apache Maven Assembly Plugin][44]                      | [Apache License, Version 2.0][31]              |
| [Apache Maven JAR Plugin][46]                           | [Apache License, Version 2.0][31]              |
| [Artifact reference checker and unifier][48]            | [MIT][5]                                       |
| [Apache Maven Dependency Plugin][50]                    | [Apache License, Version 2.0][31]              |
| [Lombok Maven Plugin][52]                               | [The MIT License][5]                           |
| [Maven Failsafe Plugin][54]                             | [Apache License, Version 2.0][31]              |
| [JaCoCo :: Maven Plugin][56]                            | [Eclipse Public License 2.0][29]               |
| [error-code-crawler-maven-plugin][58]                   | [MIT][5]                                       |
| [Project keeper maven plugin][60]                       | [MIT][5]                                       |
| [Exec Maven Plugin][62]                                 | [Apache License 2][33]                         |
| [Maven Clean Plugin][64]                                | [The Apache Software License, Version 2.0][33] |
| [Maven Resources Plugin][66]                            | [The Apache Software License, Version 2.0][33] |
| [Maven Install Plugin][68]                              | [The Apache Software License, Version 2.0][33] |
| [Maven Deploy Plugin][70]                               | [The Apache Software License, Version 2.0][33] |
| [Maven Site Plugin 3][72]                               | [The Apache Software License, Version 2.0][33] |

## Udf for Testing

### Compile Dependencies

| Dependency                             | License          |
| -------------------------------------- | ---------------- |
| [Java Interface for EXASOL Scripts][0] | [MIT License][1] |

### Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [Apache Maven Enforcer Plugin][30]                      | [Apache License, Version 2.0][31]              |
| [Maven Flatten Plugin][32]                              | [Apache Software Licenese][33]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][34] | [ASL2][33]                                     |
| [Reproducible Build Maven Plugin][36]                   | [Apache 2.0][33]                               |
| [Maven Surefire Plugin][38]                             | [Apache License, Version 2.0][31]              |
| [Versions Maven Plugin][40]                             | [Apache License, Version 2.0][31]              |
| [Apache Maven Assembly Plugin][44]                      | [Apache License, Version 2.0][31]              |
| [Apache Maven JAR Plugin][46]                           | [Apache License, Version 2.0][31]              |
| [Artifact reference checker and unifier][48]            | [MIT][5]                                       |
| [JaCoCo :: Maven Plugin][56]                            | [Eclipse Public License 2.0][29]               |
| [error-code-crawler-maven-plugin][58]                   | [MIT][5]                                       |
| [Apache Maven Compiler Plugin][98]                      | [Apache License, Version 2.0][31]              |
| [Maven Clean Plugin][64]                                | [The Apache Software License, Version 2.0][33] |
| [Maven Resources Plugin][66]                            | [The Apache Software License, Version 2.0][33] |
| [Maven Install Plugin][68]                              | [The Apache Software License, Version 2.0][33] |
| [Maven Deploy Plugin][70]                               | [The Apache Software License, Version 2.0][33] |
| [Maven Site Plugin 3][72]                               | [The Apache Software License, Version 2.0][33] |

[28]: https://www.eclemma.org/jacoco/index.html
[3]: LICENSE-exasol-jdbc.txt
[6]: https://github.com/exasol/bucketfs-java
[4]: https://github.com/exasol/error-reporting-java
[33]: http://www.apache.org/licenses/LICENSE-2.0.txt
[8]: https://projectlombok.org
[38]: https://maven.apache.org/surefire/maven-surefire-plugin/
[64]: http://maven.apache.org/plugins/maven-clean-plugin/
[5]: https://opensource.org/licenses/MIT
[26]: https://github.com/mockito/mockito
[54]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[20]: https://github.com/exasol/test-db-builder-java
[22]: https://github.com/exasol/maven-project-version-getter
[62]: http://www.mojohaus.org/exec-maven-plugin
[40]: http://www.mojohaus.org/versions-maven-plugin/
[15]: http://opensource.org/licenses/BSD-3-Clause
[98]: https://maven.apache.org/plugins/maven-compiler-plugin/
[17]: http://opensource.org/licenses/MIT
[29]: https://www.eclipse.org/legal/epl-2.0/
[18]: https://github.com/exasol/exasol-testcontainers
[56]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[27]: https://github.com/mockito/mockito/blob/main/LICENSE
[9]: https://projectlombok.org/LICENSE
[36]: http://zlika.github.io/reproducible-build-maven-plugin
[50]: https://maven.apache.org/plugins/maven-dependency-plugin/
[31]: https://www.apache.org/licenses/LICENSE-2.0.txt
[30]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[52]: https://awhitford.github.com/lombok.maven/lombok-maven-plugin/
[60]: https://github.com/exasol/project-keeper-maven-plugin/project-keeper-maven-plugin-generated-parent/project-keeper-maven-plugin
[0]: http://www.exasol.com
[11]: https://www.eclipse.org/legal/epl-v20.html
[68]: http://maven.apache.org/plugins/maven-install-plugin/
[10]: https://junit.org/junit5/
[34]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[16]: https://testcontainers.org
[32]: https://www.mojohaus.org/flatten-maven-plugin/flatten-maven-plugin
[24]: https://github.com/exasol/udf-debugging-java
[42]: http://maven.apache.org/plugins/maven-compiler-plugin/
[1]: LICENSE-exasol-script-api.txt
[14]: http://hamcrest.org/JavaHamcrest/
[70]: http://maven.apache.org/plugins/maven-deploy-plugin/
[72]: http://maven.apache.org/plugins/maven-site-plugin/
[66]: http://maven.apache.org/plugins/maven-resources-plugin/
[48]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[58]: https://github.com/exasol/error-code-crawler-maven-plugin
[46]: https://maven.apache.org/plugins/maven-jar-plugin/
[44]: https://maven.apache.org/plugins/maven-assembly-plugin/
