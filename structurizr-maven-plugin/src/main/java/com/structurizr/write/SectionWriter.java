package com.structurizr.write;

import java.io.PrintWriter;
import java.io.Writer;

public class SectionWriter {

    public static final int TAB_SIZE = 3;
    private Indentation indentation;

    private final PrintWriter writer;

    public SectionWriter(Writer writer) {
        this.writer = new PrintWriter(writer);
        this.indentation = new Indentation();
    }

    public Indentation increaseIndentation() {
        Indentation previous = indentation.copy();
        indentation = indentation.increase(TAB_SIZE);
        return previous;
    }

    public void writeLine(String template, Object... arguments) {
        writer.println(indentation.apply(template.formatted(arguments)));
    }

    public String quote(String string) {
        return "\"" + nullToEmpty(string) + "\"";
    }

    private String nullToEmpty(String string) {
        if (string == null) {
            return "";
        }
        return string;
    }

    public void resetIndentation(Indentation indentation) {
        this.indentation = indentation;
    }

    public void writeEmptyLine() {
        writer.println();
    }
}
