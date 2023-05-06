package com.structurizr.generator;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.function.Consumer;

public class ClassScanner {

    private final ClassGraph classGraph;

    public ClassScanner(String[] scanPackages, ClassLoader classLoader) {
        this.classGraph = new ClassGraph()
                .verbose()
                .enableAllInfo()
                .acceptPackages(scanPackages)
                .addClassLoader(classLoader);
    }

    public void scanClasses(Consumer<ClassInfoList> classInfoListConsumer) {
        ScanResult scan = classGraph.scan();
        createComponentsFinder()
                .get()
                .findComponents(scan)
                .forEach(classInfoListConsumer);
    }

    private Provider<ComponentsFinder> createComponentsFinder() {
        return ServiceLoader.load(ComponentsFinder.class).stream()
                .reduce((__, ___) -> {
                    throw new IllegalStateException("multiple");
                })
                .orElseThrow(() -> new NoSuchElementException("No ComponentFinder found in class path!"));
    }
}
