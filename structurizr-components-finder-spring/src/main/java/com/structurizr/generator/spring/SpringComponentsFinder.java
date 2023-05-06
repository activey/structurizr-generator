package com.structurizr.generator.spring;

import com.structurizr.generator.ComponentsFinder;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

public class SpringComponentsFinder implements ComponentsFinder {
    @Override
    public Iterable<ClassInfoList> findComponents(ScanResult classpathScanResult) {
        return Stream.of(
                classpathScanResult.getClassesWithAnnotation(Component.class),
                classpathScanResult.getClassesImplementing(Repository.class)
        ).toList();
    }
}
