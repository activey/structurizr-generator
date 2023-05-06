package com.structurizr.model;

import java.util.Optional;

public class ElementVisitor {
    public void visit(Element element) {

    }

    public void visit(Component component) {

    }

    public void visit(ComponentGroup componentGroup) {

    }

    public static class ComponentsCountingVisitor extends ElementVisitor {
        private int count;

        @Override
        public void visit(Component component) {
            count++;
        }

        public int count() {
            return this.count;
        }
    }

    public static class ComponentFindingVisitor extends ElementVisitor {

        private final String className;
        private Component foundComponent;

        public ComponentFindingVisitor(String className) {
            this.className = className;
        }

        @Override
        public void visit(Component component) {
            if (component.isOfClassName(className)) {
                this.foundComponent = component;
            }
        }

        public Optional<Component> foundComponent() {
            return Optional.ofNullable(foundComponent);
        }
    }

    public static class GroupFindingVisitor extends ElementVisitor {

        private final String groupId;
        private ComponentGroup foundGroup;

        public GroupFindingVisitor(String groupId) {
            this.groupId = groupId;
        }

        @Override
        public void visit(ComponentGroup componentGroup) {
            if (componentGroup.hasId(groupId)) {
                this.foundGroup = componentGroup;
            }
        }

        public Optional<ComponentGroup> foundGroup() {
            return Optional.ofNullable(foundGroup);
        }
    }
}
