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
| [Project Lombok][7]                    | [The MIT License][8]   |

### Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [JUnit Jupiter API][9]                          | [Eclipse Public License v2.0][10] |
| [JUnit Jupiter Params][9]                       | [Eclipse Public License v2.0][10] |
| [Hamcrest][11]                                  | [BSD License 3][12]               |
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
| [Apache Maven Compiler Plugin][29]                      | [Apache License, Version 2.0][30]              |
| [Apache Maven Enforcer Plugin][31]                      | [Apache License, Version 2.0][30]              |
| [Maven Flatten Plugin][32]                              | [Apache Software Licenese][33]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][34] | [ASL2][33]                                     |
| [Maven Surefire Plugin][35]                             | [Apache License, Version 2.0][30]              |
| [Versions Maven Plugin][36]                             | [Apache License, Version 2.0][30]              |
| [Apache Maven Assembly Plugin][37]                      | [Apache License, Version 2.0][30]              |
| [Apache Maven JAR Plugin][38]                           | [Apache License, Version 2.0][30]              |
| [Artifact reference checker and unifier][39]            | [MIT][22]                                      |
| [Apache Maven Dependency Plugin][40]                    | [Apache License, Version 2.0][30]              |
| [Lombok Maven Plugin][41]                               | [The MIT License][22]                          |
| [Project keeper maven plugin][42]                       | [The MIT License][43]                          |
| [Exec Maven Plugin][44]                                 | [Apache License 2][33]                         |
| [Maven Failsafe Plugin][45]                             | [Apache License, Version 2.0][30]              |
| [JaCoCo :: Maven Plugin][46]                            | [Eclipse Public License 2.0][26]               |
| [error-code-crawler-maven-plugin][47]                   | [MIT License][48]                              |
| [Reproducible Build Maven Plugin][49]                   | [Apache 2.0][33]                               |
| [Maven Clean Plugin][50]                                | [The Apache Software License, Version 2.0][33] |
| [Maven Resources Plugin][51]                            | [The Apache Software License, Version 2.0][33] |
| [Maven Install Plugin][52]                              | [The Apache Software License, Version 2.0][33] |
| [Maven Deploy Plugin][53]                               | [The Apache Software License, Version 2.0][33] |
| [Maven Site Plugin 3][54]                               | [The Apache Software License, Version 2.0][33] |

## Udf for Testing

### Compile Dependencies

| Dependency                             | License          |
| -------------------------------------- | ---------------- |
| [Java Interface for EXASOL Scripts][0] | [MIT License][1] |

### Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][27]                       | [GNU LGPL 3][28]                               |
| [Apache Maven Compiler Plugin][29]                      | [Apache License, Version 2.0][30]              |
| [Apache Maven Enforcer Plugin][31]                      | [Apache License, Version 2.0][30]              |
| [Maven Flatten Plugin][32]                              | [Apache Software Licenese][33]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][34] | [ASL2][33]                                     |
| [Maven Surefire Plugin][35]                             | [Apache License, Version 2.0][30]              |
| [Versions Maven Plugin][36]                             | [Apache License, Version 2.0][30]              |
| [Apache Maven Assembly Plugin][37]                      | [Apache License, Version 2.0][30]              |
| [Apache Maven JAR Plugin][38]                           | [Apache License, Version 2.0][30]              |
| [Artifact reference checker and unifier][39]            | [MIT][22]                                      |
| [JaCoCo :: Maven Plugin][46]                            | [Eclipse Public License 2.0][26]               |
| [error-code-crawler-maven-plugin][47]                   | [MIT License][48]                              |
| [Reproducible Build Maven Plugin][49]                   | [Apache 2.0][33]                               |
| [Maven Clean Plugin][50]                                | [The Apache Software License, Version 2.0][33] |
| [Maven Resources Plugin][51]                            | [The Apache Software License, Version 2.0][33] |
| [Maven Install Plugin][52]                              | [The Apache Software License, Version 2.0][33] |
| [Maven Deploy Plugin][53]                               | [The Apache Software License, Version 2.0][33] |
| [Maven Site Plugin 3][54]                               | [The Apache Software License, Version 2.0][33] |

[0]: http://www.exasol.com
[1]: LICENSE-exasol-script-api.txt
[2]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
[3]: https://github.com/exasol/error-reporting-java/
[4]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[5]: https://github.com/exasol/bucketfs-java/
[6]: https://github.com/exasol/bucketfs-java/blob/main/LICENSE
[7]: https://projectlombok.org
[8]: https://projectlombok.org/LICENSE
[9]: https://junit.org/junit5/
[10]: https://www.eclipse.org/legal/epl-v20.html
[11]: http://hamcrest.org/JavaHamcrest/
[12]: http://opensource.org/licenses/BSD-3-Clause
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
[30]: https://www.apache.org/licenses/LICENSE-2.0.txt
[31]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[32]: https://www.mojohaus.org/flatten-maven-plugin/
[33]: http://www.apache.org/licenses/LICENSE-2.0.txt
[34]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[35]: https://maven.apache.org/surefire/maven-surefire-plugin/
[36]: http://www.mojohaus.org/versions-maven-plugin/
[37]: https://maven.apache.org/plugins/maven-assembly-plugin/
[38]: https://maven.apache.org/plugins/maven-jar-plugin/
[39]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[40]: https://maven.apache.org/plugins/maven-dependency-plugin/
[41]: https://anthonywhitford.com/lombok.maven/lombok-maven-plugin/
[42]: https://github.com/exasol/project-keeper/
[43]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[44]: http://www.mojohaus.org/exec-maven-plugin
[45]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[46]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[47]: https://github.com/exasol/error-code-crawler-maven-plugin/
[48]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[49]: http://zlika.github.io/reproducible-build-maven-plugin
[50]: http://maven.apache.org/plugins/maven-clean-plugin/
[51]: http://maven.apache.org/plugins/maven-resources-plugin/
[52]: http://maven.apache.org/plugins/maven-install-plugin/
[53]: http://maven.apache.org/plugins/maven-deploy-plugin/
[54]: http://maven.apache.org/plugins/maven-site-plugin/
