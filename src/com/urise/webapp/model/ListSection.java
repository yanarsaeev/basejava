package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private static final long serialVersionUID = 1L;

    private List<String> strings;

    public ListSection() {
    }

    public ListSection(String... strings) {
        this(Arrays.asList(strings));
    }

    public ListSection(List<String> strings) {
        this.strings = strings;
    }

    public void addString(String string) {
        strings.add(string);
    }

    public List<String> getStrings() {
        return strings;
    }


    @Override
    public String toString() {
        return "ListSection{" +
                "strings=" + strings +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(strings, that.strings);
    }

    @Override
    public int hashCode() {
        return strings.hashCode();
    }
}
