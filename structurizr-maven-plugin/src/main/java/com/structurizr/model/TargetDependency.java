package com.structurizr.model;

import com.structurizr.StructurizrContainerRelations;

public class TargetDependency extends AbstractDependency {

    private final Component sourceComponent;
    private final String target;

    public TargetDependency(Component sourceComponent, String target, String description) {
        super(description);
        this.sourceComponent = sourceComponent;
        this.target = target;
    }

    @Override
    public void buildRelation(Container container, StructurizrContainerRelations relations) {
        relations.addRelation(
                sourceComponent.getName(),
                target,
                getDescription(),
                getTechnology(),
                getTags()
        );
    }
}
