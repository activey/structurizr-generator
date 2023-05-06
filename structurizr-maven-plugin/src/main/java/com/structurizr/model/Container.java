package com.structurizr.model;

import com.structurizr.StructurizrContainerRelations;
import com.structurizr.StructurizrGroup;
import com.structurizr.write.Indentation;
import com.structurizr.write.SectionWriter;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Container extends ContainingElement {

    private final String id;
    private final String name;
    private final String description;
    private final String technology;

    public Container(String id, String name, String description, String technology) {
        this.id = id == null ? UUID.randomUUID().toString().replaceAll("-", "") : id;
        this.name = name;
        this.description = description;
        this.technology = technology;
    }

    public StructurizrContainerRelations setupRelations() {
        StructurizrContainerRelations relations = new StructurizrContainerRelations();
        accept(new ElementVisitor() {
            @Override
            public void visit(Component component) {
                component
                        .getDependencies()
                        .forEach(dependency -> dependency.buildRelation(Container.this, relations));
            }
        });
        return relations;
    }

    public ContainingElement setupGroupsHierarchy(List<StructurizrGroup> groupPackages) {
        if (groupPackages == null) {
            return this;
        }
        return setupGroupsHierarchy(this, groupPackages.iterator());
    }

    private ContainingElement setupGroupsHierarchy(ContainingElement parent, Iterator<StructurizrGroup> groupIterator) {
        if (groupIterator.hasNext()) {
            StructurizrGroup group = groupIterator.next();
            return parent
                    .findGroupById(group.getId())
                    .map(componentGroup -> setupGroupsHierarchy(componentGroup, groupIterator))
                    .orElseGet(() -> parent.addGroup(group.getId(), group.getName()));
        }
        return parent;
    }

    public void toDSL(SectionWriter writer) throws IOException {
        writer.writeLine("%s = container %s %s %s {", id, writer.quote(name), writer.quote(description), writer.quote(technology));

        Indentation indentation = writer.increaseIndentation();
        super.toDSL(writer);
        indentation.reset(writer);

        writer.writeLine("}");
        writer.writeEmptyLine();
    }
}
