package com.structurizr.write;

public class Indentation {

    private final int value;

    public Indentation(int value) {
        this.value = value;
    }

    public Indentation() {
        this(0);
    }

    public Indentation copy() {
        return new Indentation(value);
    }

    public Indentation increase(int tabSize) {
        return new Indentation(value + tabSize);
    }

    public void reset(SectionWriter writer) {
        writer.resetIndentation(this);
    }

    public String apply(String textToIndent) {
        return " ".repeat(value) + textToIndent;
    }
}
