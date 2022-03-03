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

### Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [Apache Maven Enforcer Plugin][26]                      | [Apache License, Version 2.0][27]              |
| [Maven Flatten Plugin][28]                              | [Apache Software Licenese][29]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][30] | [ASL2][29]                                     |
| [Reproducible Build Maven Plugin][32]                   | [Apache 2.0][29]                               |
| [Maven Surefire Plugin][34]                             | [Apache License, Version 2.0][27]              |
| [Versions Maven Plugin][36]                             | [Apache License, Version 2.0][27]              |
| [Maven Compiler Plugin][38]                             | [The Apache Software License, Version 2.0][29] |
| [Apache Maven Assembly Plugin][40]                      | [Apache License, Version 2.0][27]              |
| [Apache Maven JAR Plugin][42]                           | [Apache License, Version 2.0][27]              |
| [Artifact reference checker and unifier][44]            | [MIT][5]                                       |
| [Lombok Maven Plugin][46]                               | [The MIT License][5]                           |
| [Maven Failsafe Plugin][48]                             | [Apache License, Version 2.0][27]              |
| [JaCoCo :: Maven Plugin][50]                            | [Eclipse Public License 2.0][51]               |
| [error-code-crawler-maven-plugin][52]                   | [MIT][5]                                       |
| [Project keeper maven plugin][54]                       | [MIT][5]                                       |
| [Maven Clean Plugin][56]                                | [The Apache Software License, Version 2.0][29] |
| [Maven Resources Plugin][58]                            | [The Apache Software License, Version 2.0][29] |
| [Maven Install Plugin][60]                              | [The Apache Software License, Version 2.0][29] |
| [Maven Deploy Plugin][62]                               | [The Apache Software License, Version 2.0][29] |
| [Maven Site Plugin 3][64]                               | [The Apache Software License, Version 2.0][29] |

## Udf for Testing

### Compile Dependencies

| Dependency                             | License          |
| -------------------------------------- | ---------------- |
| [Java Interface for EXASOL Scripts][0] | [MIT License][1] |

### Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [Apache Maven Enforcer Plugin][26]                      | [Apache License, Version 2.0][27]              |
| [Maven Flatten Plugin][28]                              | [Apache Software Licenese][29]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][30] | [ASL2][29]                                     |
| [Reproducible Build Maven Plugin][32]                   | [Apache 2.0][29]                               |
| [Maven Surefire Plugin][34]                             | [Apache License, Version 2.0][27]              |
| [Versions Maven Plugin][36]                             | [Apache License, Version 2.0][27]              |
| [Apache Maven Assembly Plugin][40]                      | [Apache License, Version 2.0][27]              |
| [Apache Maven JAR Plugin][42]                           | [Apache License, Version 2.0][27]              |
| [Artifact reference checker and unifier][44]            | [MIT][5]                                       |
| [JaCoCo :: Maven Plugin][50]                            | [Eclipse Public License 2.0][51]               |
| [error-code-crawler-maven-plugin][52]                   | [MIT][5]                                       |
| [Apache Maven Compiler Plugin][90]                      | [Apache License, Version 2.0][27]              |
| [Maven Clean Plugin][56]                                | [The Apache Software License, Version 2.0][29] |
| [Maven Resources Plugin][58]                            | [The Apache Software License, Version 2.0][29] |
| [Maven Install Plugin][60]                              | [The Apache Software License, Version 2.0][29] |
| [Maven Deploy Plugin][62]                               | [The Apache Software License, Version 2.0][29] |
| [Maven Site Plugin 3][64]                               | [The Apache Software License, Version 2.0][29] |

[3]: LICENSE-exasol-jdbc.txt
[6]: https://github.com/exasol/bucketfs-java
[4]: https://github.com/exasol/error-reporting-java
[29]: http://www.apache.org/licenses/LICENSE-2.0.txt
[8]: https://projectlombok.org
[34]: https://maven.apache.org/surefire/maven-surefire-plugin/
[56]: http://maven.apache.org/plugins/maven-clean-plugin/
[5]: https://opensource.org/licenses/MIT
[48]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[20]: https://github.com/exasol/test-db-builder-java
[22]: https://github.com/exasol/maven-project-version-getter
[36]: http://www.mojohaus.org/versions-maven-plugin/
[15]: http://opensource.org/licenses/BSD-3-Clause
[90]: https://maven.apache.org/plugins/maven-compiler-plugin/
[17]: http://opensource.org/licenses/MIT
[51]: https://www.eclipse.org/legal/epl-2.0/
[18]: https://github.com/exasol/exasol-testcontainers
[50]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[9]: https://projectlombok.org/LICENSE
[32]: http://zlika.github.io/reproducible-build-maven-plugin
[27]: https://www.apache.org/licenses/LICENSE-2.0.txt
[26]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[46]: https://awhitford.github.com/lombok.maven/lombok-maven-plugin/
[54]: https://github.com/exasol/project-keeper-maven-plugin/project-keeper-maven-plugin-generated-parent/project-keeper-maven-plugin
[0]: http://www.exasol.com
[11]: https://www.eclipse.org/legal/epl-v20.html
[60]: http://maven.apache.org/plugins/maven-install-plugin/
[10]: https://junit.org/junit5/
[30]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[16]: https://testcontainers.org
[28]: https://www.mojohaus.org/flatten-maven-plugin/flatten-maven-plugin
[24]: https://github.com/exasol/udf-debugging-java
[38]: http://maven.apache.org/plugins/maven-compiler-plugin/
[1]: LICENSE-exasol-script-api.txt
[14]: http://hamcrest.org/JavaHamcrest/
[62]: http://maven.apache.org/plugins/maven-deploy-plugin/
[64]: http://maven.apache.org/plugins/maven-site-plugin/
[58]: http://maven.apache.org/plugins/maven-resources-plugin/
[44]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[52]: https://github.com/exasol/error-code-crawler-maven-plugin
[42]: https://maven.apache.org/plugins/maven-jar-plugin/
[40]: https://maven.apache.org/plugins/maven-assembly-plugin/
