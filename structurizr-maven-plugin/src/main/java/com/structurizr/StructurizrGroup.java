package com.structurizr;

public class StructurizrGroup {

    private final String id;
    private final String name;

    public StructurizrGroup(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
