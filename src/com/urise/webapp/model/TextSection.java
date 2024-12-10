package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends Section {
    private static final long serialVersionUID = 1L;

    private String content;

    public TextSection() {}

    public TextSection(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    public String getContent() {
        return content;
    }
}
