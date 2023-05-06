package com.structurizr.mojo;

import com.structurizr.StructurizrContainerFactory;
import com.structurizr.StructurizrContainerRelations;
import com.structurizr.model.Container;
import com.structurizr.write.SectionWriter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;


@Mojo(name = "dsl-generator", defaultPhase = LifecyclePhase.COMPILE)
public class StructurizerDSLGeneratorMojo extends AbstractMojo {

    private static final String CONTAINER_DSL = "container.dsl";

    @Parameter(required = true)
    String[] scanPackages;

    @Parameter
    Exclusions exclusions;

    @Parameter(required = true)
    File outputDirectory;

    @Parameter(required = true)
    StructurizrContainer container;

    @Parameter(required = true, defaultValue = "${project}")
    MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Container container = new Container(
                this.container.getId(),
                this.container.getName(),
                this.container.getDescription(),
                this.container.getTechnology()
        );

        StructurizrContainerFactory containerFactory = new StructurizrContainerFactory(scanPackages, exclusions, getMavenClassLoader());
        StructurizrContainerRelations relations = containerFactory
                .fillContainer(container)
                .setupRelations();

        getLog().info("Components found: %d".formatted(container.countComponents()));
        StringWriter writer = new StringWriter();
        SectionWriter sectionWriter = new SectionWriter(writer);
        try {
            container.toDSL(sectionWriter);
            relations.toDSL(sectionWriter);

            Path outputPath = outputDirectory.toPath();
            if (!Files.exists(outputPath)) {
                Files.createDirectories(outputPath);
            }

            Files.writeString(
                    outputPath.resolve(CONTAINER_DSL),
                    writer.getBuffer(),
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            getLog().error(e);
        }
    }

    private ClassLoader getMavenClassLoader() {
        try {
            List<String> classpathElements = project.getCompileClasspathElements();
            classpathElements.add(project.getBuild().getOutputDirectory());
            classpathElements.add(project.getBuild().getTestOutputDirectory());
            URL urls[] = new URL[classpathElements.size()];
            for (int index = 0; index < classpathElements.size(); ++index) {
                urls[index] = new File((String) classpathElements.get(index)).toURL();
            }
            return new URLClassLoader(urls, this.getClass().getClassLoader());
        } catch (Exception e) {
            getLog().debug("Couldn't get the classloader.");
            return this.getClass().getClassLoader();
        }
    }
}
