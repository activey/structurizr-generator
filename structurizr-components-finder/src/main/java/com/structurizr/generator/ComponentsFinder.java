package com.structurizr.generator;

import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

public interface ComponentsFinder {

    Iterable<ClassInfoList> findComponents(ScanResult classpathScanResult);
}