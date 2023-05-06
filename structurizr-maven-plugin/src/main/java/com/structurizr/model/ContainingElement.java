package com.structurizr.model;

import com.structurizr.model.ElementVisitor.ComponentFindingVisitor;
import com.structurizr.model.ElementVisitor.ComponentsCountingVisitor;
import com.structurizr.model.ElementVisitor.GroupFindingVisitor;
import com.structurizr.write.SectionWriter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class ContainingElement extends Element {

    private final List<Element> elements;

    public ContainingElement() {
        this.elements = new LinkedList<>();
    }

    protected void addElement(Element element) {
        elements.add(element);
    }

    public Component addComponent(String name, String className) {
        // , List<? extends AbstractDependency> dependencies
        Component component = new Component(name, className);
        addElement(component);
        return component;
    }

    public ComponentGroup addGroup(String id, String name) {
        ComponentGroup componentGroup = new ComponentGroup(id, name);
        addElement(componentGroup);
        return componentGroup;
    }

    public int countComponents() {
        ComponentsCountingVisitor componentsCountingVisitor = new ComponentsCountingVisitor();
        elements.forEach(element -> element.accept(componentsCountingVisitor));
        return componentsCountingVisitor.count();
    }

    public Optional<Component> findComponentByClass(String className) {
        ComponentFindingVisitor componentFindingVisitor = new ComponentFindingVisitor(className);
        accept(componentFindingVisitor);
        return componentFindingVisitor.foundComponent();
    }

    protected Optional<ComponentGroup> findGroupById(String groupId) {
        GroupFindingVisitor groupFindingVisitor = new GroupFindingVisitor(groupId);
        accept(groupFindingVisitor);
        return groupFindingVisitor.foundGroup();
    }

    @Override
    public void toDSL(SectionWriter writer) throws IOException {
        for (Element element : elements) element.toDSL(writer);
    }

    @Override
    public void accept(ElementVisitor elementVisitor) {
        elementVisitor.visit(this);
        elements.forEach(element -> element.accept(elementVisitor));
    }
}
