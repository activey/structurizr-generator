package com.structurizr.mojo;

import io.github.classgraph.ClassInfo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class Exclusions {

    @Parameter(name = "supertypes")
    String[] supertypes;

    public boolean doesNotMatch(ClassInfo classInfo) {
        return Stream.of(supertypes).noneMatch(
                implementsInterface(classInfo).or(extendsClass(classInfo))
        );
    }

    private Predicate<String> implementsInterface(ClassInfo classInfo) {
        return classInfo::implementsInterface;
    }

    private Predicate<String> extendsClass(ClassInfo classInfo) {
        return classInfo::extendsSuperclass;
    }
}
