package com.structurizr;

import com.structurizr.generator.ClassScanner;
import com.structurizr.model.Container;
import com.structurizr.annotations.Group;
import com.structurizr.model.Component;
import com.structurizr.model.ContainingElement;
import com.structurizr.mojo.Exclusions;
import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.PackageInfo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class StructurizrContainerFactory {

    private final String[] scanPackages;
    private final ClassScanner classScanner;

    private final Exclusions exclusions;

    public StructurizrContainerFactory(String[] scanPackages, Exclusions exclusions, ClassLoader classLoader) {
        this.scanPackages = scanPackages;
        this.exclusions = exclusions == null ? new Exclusions() : exclusions;
        this.classScanner = new ClassScanner(scanPackages, classLoader);
    }

    public Container fillContainer(Container container) {
        classScanner.scanClasses(classInfos -> addComponents(container, classInfos));
        return container;
    }

    private void addComponents(Container container, ClassInfoList componentsTypes) {
        if (componentsTypes == null || componentsTypes.isEmpty()) {
            return;
        }
        componentsTypes
                .filter(classInfo -> Stream.of(scanPackages).anyMatch(scanPackage -> classInfo.getPackageName().startsWith(scanPackage)))
                .filter(exclusions::doesNotMatch)
                .forEach(componentType -> {
                    List<StructurizrGroup> groupPackages = collectGroupPackages(componentType.getPackageInfo(), new LinkedList<>());
                    Collections.reverse(groupPackages);
                    ContainingElement containingElement = container.setupGroupsHierarchy(groupPackages);

                    Component component = containingElement.addComponent(
                            componentType.getSimpleName(),
                            componentType.getName()
                    );
                    component.collectDependencies(componentType);
                    component.collectTags(componentType);
                });
    }

    private List<StructurizrGroup> collectGroupPackages(PackageInfo packageInfo, List<StructurizrGroup> groups) {
        if (packageInfo == null) {
            return groups;
        }
        if (packageInfo.hasAnnotation(Group.class)) {
            AnnotationInfo annotationInfo = packageInfo.getAnnotationInfo(Group.class);
            String groupName = Optional.ofNullable(annotationInfo.getParameterValues().getValue("value"))
                    .map(Object::toString)
                    .orElse(packageInfo.getName());
            groups.add(new StructurizrGroup(packageInfo.getName(), groupName));
        }
        return collectGroupPackages(packageInfo.getParent(), groups);
    }
}
