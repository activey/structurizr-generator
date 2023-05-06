package com.structurizr.model;

import com.structurizr.annotations.Tags;
import com.structurizr.annotations.Uses;
import com.structurizr.annotations.UsedBy;
import com.structurizr.write.Indentation;
import com.structurizr.write.SectionWriter;
import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.FieldInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Component extends Element {

    private final String name;
    private final String className;
    private final List<AbstractDependency> dependencies;
    private List<String> tags;

    public Component(String name, String className) {
        this.name = name;
        this.className = className;
        this.tags = new LinkedList<>();
        this.dependencies = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public List<? extends AbstractDependency> getDependencies() {
        return dependencies;
    }

    private String lowerCamelCase(String name) {
        return name; // TODO
    }

    public boolean isOfClassName(String className) {
        return this.className.equals(className);
    }

    public void collectDependencies(ClassInfo classInfo) {
        // @Uses
        AnnotationInfo usesAnnotation = classInfo.getAnnotationInfo(Uses.class);
        if (usesAnnotation != null) {
            Optional
                    .ofNullable(usesAnnotation.getParameterValues().getValue("target"))
                    .map(Object::toString)
                    .ifPresent(target -> dependencies.add(
                            new TargetDependency(this, target, Optional.ofNullable(usesAnnotation
                                    .getParameterValues()
                                    .getValue("value")
                            ).map(Object::toString).orElse("")).applyCommonAnnotationAttributes(usesAnnotation)));
        }

        // @UsedBy
        AnnotationInfo usedByAnnotation = classInfo.getAnnotationInfo(UsedBy.class);
        if (usedByAnnotation != null) {
            Optional
                    .ofNullable(usedByAnnotation.getParameterValues().getValue("source"))
                    .map(Object::toString)
                    .ifPresent(source -> dependencies.add(
                            new SourceDependency(this, source, Optional.ofNullable(usedByAnnotation
                                    .getParameterValues()
                                    .getValue("value")
                            ).map(Object::toString).orElse("")).applyCommonAnnotationAttributes(usedByAnnotation)));
        }

        // referenced types in fields
        classInfo
                .getFieldInfo()
                .stream()
                .map(this::componentDependency)
                .forEach(componentDependency -> dependencies.add(componentDependency));

    }

    private ComponentDependency componentDependency(FieldInfo fieldInfo) {
        ComponentDependency dependency = new ComponentDependency(this, fieldInfo.getTypeDescriptor().toString());
        AnnotationInfo annotationInfo = fieldInfo.getAnnotationInfo(Uses.class);
        if (annotationInfo != null) {
            dependency.applyCommonAnnotationAttributes(annotationInfo);
        }
        return dependency;
    }


    public void collectTags(ClassInfo classInfo) {
        if (!classInfo.hasAnnotation(Tags.class)) {
            return;
        }
        AnnotationInfo annotationInfo = classInfo.getAnnotationInfo(Tags.class);
        tags = Optional
                .ofNullable(annotationInfo.getParameterValues().getValue("value"))
                .map(String[].class::cast)
                .map(List::of)
                .orElse(List.of());
    }

    @Override
    public void accept(ElementVisitor elementVisitor) {
        elementVisitor.visit(this);
        super.accept(elementVisitor);
    }

    public void toDSL(SectionWriter writer) {
        writer.writeLine("%s = component %s {", lowerCamelCase(name), writer.quote(name));
        if (tags.size() > 0) {
            Indentation indentation = writer.increaseIndentation();
            writer.writeLine("tags %s", String.join(" ", tags));
            writer.resetIndentation(indentation);
        }
        writer.writeLine("}");
        writer.writeEmptyLine();
    }
}
