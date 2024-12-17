package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String website;
    private List<Period> periods;

    public Company() {}

    public Company(String name, String website) {
        this.name = name;
        this.website = website;
        this.periods = new ArrayList<>();
    }

    public Company(String name, String website, List<Period> periods) {
        this.name = name;
        this.website = website;
        this.periods = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    @Override
    public String toString() {
        return "Name: " + this.name +
                "\nWebsite: " + this.website +
                "\nPeriods: " + this.periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) &&
                Objects.equals(website, company.website) &&
                Objects.equals(periods, company.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, website, periods);
    }
}
