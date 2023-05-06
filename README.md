This project contains a dedicated maven plugin for generating a [Structurizr](https://structurizr.com/) DSL code representing a single C4 container,
which then can be included as a part of other, bigger eco-system.

## Plugin usage
Current plugin version in its early development stage supports only Spring based applications and processes
all Java classes in selected packages conforming given criteria:

- class is annotated with Spring `@Component` annotation or its derivative, eg: `@Service`, `@RestController`, etc,
- or implements `org.springframework.data.repository.Repository` or its supertype, eg: `org.springframework.data.r2dbc.repository.R2dbcRepository`

Here is an example plugin configuration (from [structurizr-spring-demo](structurizr-spring-demo)):
```xml
<project>
    ...
    <properties>
        <structurizr-generator.version>0.0.1</structurizr-generator.version>
    </properties>
    ...
    <build>
        <plugins>
            <plugin>
                <groupId>com.structurizr.generator</groupId>
                <artifactId>structurizr-maven-plugin</artifactId>
                <version>${structurizr-generator.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>dsl-generator</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <scanPackages>
                        <scanPackage>com.structurizr.demo</scanPackage>
                    </scanPackages>
                    <exclusions>
                        <supertypes>
                            <supertype>org.springframework.core.convert.converter.Converter</supertype>
                        </supertypes>
                    </exclusions>
                    <container>
                        <id>test</id>
                        <name>Test service</name>
                    </container>
                    <outputDirectory>${project.basedir}/c4/model</outputDirectory>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>com.structurizr.generator</groupId>
                        <artifactId>structurizr-components-finder-spring</artifactId>
                        <version>${structurizr-generator.version}</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
</project>
```
As an output of its actions plugin generates a single `container.dsl` file under location determined by
`outputDirectory` configuration parameter. To run the plugin goal follow the Maven goal naming strategy:
```bash
mvn structurizr:dsl-generator
```

## Plugin configuration
Maven plugin itself gives certain level of configurability by setting few configuration parameters.

| Parameter         | Meaning                                                                         | Type                                      |
|-------------------|---------------------------------------------------------------------------------|-------------------------------------------|
| `scanPackages`    | List of java packages to be scanned in order to extract Components in Container | java.lang.String[]                        |
| `outputDirectory` | Target location of generated `container.dsl` file                               | java.io.File                              |
| `container`       | Configuration of the Container to be generated.                                 | com.structurizr.mojo.StructurizrContainer |
| `exclusions`      | List of types to be excluded from Components extraction                         | com.structurizr.mojo.Exclusions           |

**com.structurizr.mojo.StructurizrContainer**

| Parameter | Meaning                                                                 | Type             |
|-----------|-------------------------------------------------------------------------|------------------|
| `id`      | This value will be used as a name of the container declaration variable | java.lang.String |
| `name`    | Target name of Structurizr Component element                            | java.lang.String |

**com.structurizr.mojo.Exclusions**

| Parameter    | Meaning                                                                                 | Type             |
|--------------|-----------------------------------------------------------------------------------------|------------------|
| `supertypes` | Collection of all all ignored supertypes (extending classes or implementing interfaces) | java.lang.String |

## Plugin output
When ran against [structurizr-spring-demo](structurizr-spring-demo) and converted to Mermaid produces given output:

```mermaid
graph TB
  linkStyle default fill:#ffffff

  subgraph diagram [Spring Demo - Test service - Components]
    style diagram fill:#ffffff,stroke:#ffffff

    4["<div style='font-weight: bold'>API Gateway</div><div style='font-size: 70%; margin-top: 0px'>[Container]</div><div style='font-size: 80%; margin-top:10px'>NGNIX</div>"]
    style 4 fill:#dddddd,stroke:#9a9a9a,color:#000000

    subgraph 6 [Test service]
      style 6 fill:#ffffff,stroke:#9a9a9a,color:#9a9a9a

      subgraph group1 [Module1]
        style group1 fill:#ffffff,stroke:#cccccc,color:#cccccc,stroke-dasharray:5

        8["<div style='font-weight: bold'>Component1</div><div style='font-size: 70%; margin-top: 0px'>[Component]</div>"]
        style 8 fill:#dddddd,stroke:#9a9a9a,color:#000000
        9["<div style='font-weight: bold'>Service1</div><div style='font-size: 70%; margin-top: 0px'>[Component]</div>"]
        style 9 fill:#dddddd,stroke:#9a9a9a,color:#000000
      end

      subgraph group2 [Module2]
        style group2 fill:#ffffff,stroke:#cccccc,color:#cccccc,stroke-dasharray:5

        10["<div style='font-weight: bold'>Service2</div><div style='font-size: 70%; margin-top: 0px'>[Component]</div>"]
        style 10 fill:#dddddd,stroke:#9a9a9a,color:#000000
          subgraph group3 [infrastructure]
            style group3 fill:#ffffff,stroke:#cccccc,color:#cccccc,stroke-dasharray:5

            11["<div style='font-weight: bold'>RestController2</div><div style='font-size: 70%; margin-top: 0px'>[Component]</div>"]
            style 11 fill:#dddddd,stroke:#9a9a9a,color:#000000
          end

      end

      7["<div style='font-weight: bold'>SingleComponent</div><div style='font-size: 70%; margin-top: 0px'>[Component]</div>"]
      style 7 fill:#dddddd,stroke:#9a9a9a,color:#000000
    end

    10-- "<div></div><div style='font-size: 70%'></div>" -->9
    9-. "<div>Calls component</div><div style='font-size: 70%'>[JVM]</div>" .->8
    4-. "<div>Calls the controller</div><div style='font-size: 70%'>[HTTPS]</div>" .->11
    11-- "<div></div><div style='font-size: 70%'></div>" -->10
  end
```

# Java annotations
TODO: write usage description