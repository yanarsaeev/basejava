package com.urise.webapp.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {
    private static final long serialVersionUID = 1L;

    private final List<Company> companies;

    public CompanySection() {
        this.companies = new LinkedList<>();
    }

    public void addCompany(Company company) {
        companies.add(company);
    }

    @Override
    public String toString() {
        return "CompanySection{" +
                "companies=" + companies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(companies, that.companies);
    }

    @Override
    public int hashCode() {
        return companies.hashCode();
    }
}
