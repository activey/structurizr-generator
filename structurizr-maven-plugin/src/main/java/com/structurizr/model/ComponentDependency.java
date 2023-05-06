package com.structurizr.model;

import com.structurizr.StructurizrContainerRelations;

public class ComponentDependency extends AbstractDependency {

    private final Component sourceComponent;
    private final String targetComponentClassName;

    public ComponentDependency(Component sourceComponent, String targetComponentClassName) {
        this(sourceComponent, targetComponentClassName, "");
    }

    public ComponentDependency(Component sourceComponent, String targetComponentClassName, String description) {
        super(description);
        this.sourceComponent = sourceComponent;
        this.targetComponentClassName = targetComponentClassName;
    }

    @Override
    public void buildRelation(Container container, StructurizrContainerRelations relations) {
        container
                .findComponentByClass(targetComponentClassName)
                .ifPresent(component -> relations.addRelation(
                        sourceComponent.getName(),
                        component.getName(),
                        getDescription(),
                        getTechnology(),
                        getTags()
                ));
    }
}
