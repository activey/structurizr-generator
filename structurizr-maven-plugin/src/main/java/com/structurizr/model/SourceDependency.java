package com.structurizr.model;

import com.structurizr.StructurizrContainerRelations;

public class SourceDependency extends AbstractDependency {

    private final Component targetComponent;
    private final String source;

    public SourceDependency(Component targetComponent, String source, String description) {
        super(description);
        this.targetComponent = targetComponent;
        this.source = source;
    }

    @Override
    public void buildRelation(Container container, StructurizrContainerRelations relations) {
        relations.addRelation(
                source,
                targetComponent.getName(),
                getDescription(),
                getTechnology(),
                getTags()
        );
    }
}
