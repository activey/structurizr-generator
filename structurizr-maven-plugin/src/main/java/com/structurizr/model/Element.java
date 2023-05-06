package com.structurizr.model;

import com.structurizr.write.SectionWriter;

import java.io.IOException;

public abstract class Element {

    public abstract void toDSL(SectionWriter writer) throws IOException;

    public void accept(ElementVisitor elementVisitor) {
        elementVisitor.visit(this);
    }
}
