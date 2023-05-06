package com.structurizr.mojo;

import org.apache.maven.plugins.annotations.Parameter;

public class StructurizrContainer {

    @Parameter(property = "id")
    String id;

    @Parameter(property = "name", required = true)
    String name;

    @Parameter(property = "description")
    String description;
    @Parameter(property = "technology")
    String technology;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
}
