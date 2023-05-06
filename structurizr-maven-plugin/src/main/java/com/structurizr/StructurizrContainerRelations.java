package com.structurizr;

import com.structurizr.write.SectionWriter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StructurizrContainerRelations {

    private final Map<String, List<StructurizrRelation>> relations;

    public StructurizrContainerRelations() {
        this.relations = new HashMap<>();
    }

    public void addRelation(String source,
                            String target,
                            String description,
                            String technology,
                            List<String> tags) {
        relations
                .computeIfAbsent(source, key -> new LinkedList<>())
                .add(new StructurizrRelation(target, description, technology, tags));
    }

    public void toDSL(SectionWriter sectionWriter) {
        relations.forEach((source, relationsList) ->
                relationsList.forEach(relation -> sectionWriter.writeLine(
                        "%s -> %s %s %s %s",
                        source,
                        relation.getTarget(),
                        sectionWriter.quote(relation.getDescription()),
                        sectionWriter.quote(relation.getTechnology()),
                        relation.getTags()
                                .stream()
                                .map(sectionWriter::quote)
                                .collect(Collectors.joining(" "))
                )));
    }
}
