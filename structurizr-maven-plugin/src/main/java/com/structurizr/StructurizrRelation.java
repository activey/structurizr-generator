package com.structurizr;

import java.util.List;

public class StructurizrRelation {

    private final String target;
    private final String technology;
    private final List<String> tags;
    private final String description;

    public StructurizrRelation(String target, String description, String technology, List<String> tags) {
        this.target = target;
        this.description = description;
        this.technology = technology;
        this.tags = tags;
    }

    public String getTarget() {
        return target;
    }

    public String getDescription() {
        return description;
    }

    public String getTechnology() {
        return technology;
    }

    public List<String> getTags() {
        return tags;
    }
}
