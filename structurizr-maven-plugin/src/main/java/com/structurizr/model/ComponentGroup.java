package com.structurizr.model;

import com.structurizr.write.Indentation;
import com.structurizr.write.SectionWriter;

import java.io.IOException;

public class ComponentGroup extends ContainingElement {
    private final String id;
    private final String name;

    public ComponentGroup(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void toDSL(SectionWriter writer) throws IOException {
        writer.writeLine("group %s {", writer.quote(name));

        Indentation indentation = writer.increaseIndentation();
        super.toDSL(writer);
        indentation.reset(writer);

        writer.writeLine("}");
        writer.writeEmptyLine();
    }

    public boolean hasId(String groupId) {
        return id.equals(groupId);
    }

    @Override
    public void accept(ElementVisitor elementVisitor) {
        elementVisitor.visit(this);
        super.accept(elementVisitor);
    }
}
