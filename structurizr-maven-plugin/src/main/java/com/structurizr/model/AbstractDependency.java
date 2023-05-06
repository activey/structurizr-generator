package com.structurizr.model;

import com.structurizr.StructurizrContainerRelations;
import io.github.classgraph.AnnotationInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDependency {
    private String description;
    private List<String> tags;
    private String technology;

    public AbstractDependency(String description) {
        this.description = description;
        this.tags = new LinkedList<>();
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public abstract void buildRelation(Container container, StructurizrContainerRelations relations);

    public AbstractDependency applyCommonAnnotationAttributes(AnnotationInfo annotationInfo) {
        setTags(Optional.ofNullable(annotationInfo.getParameterValues().getValue("tags")).map(String[].class::cast).map(List::of).orElse(List.of()));
        setDescription(Optional.ofNullable(annotationInfo.getParameterValues().getValue("value")).map(Object::toString).orElse(""));
        setTechnology((Optional.ofNullable(annotationInfo.getParameterValues().getValue("technology")).map(Object::toString).orElse("")));
        return this;
    }
}
