package com.urise.webapp.model;

import java.time.LocalDate;

import static java.time.LocalDate.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Ali Aliev");
        LocalDate start1 = of(2020, 6, 11);
        LocalDate end1 = of(2021, 5, 5);
        Period period1 = new Period(start1, end1, "Java Developer", "Spring, Java, SQL");

        LocalDate start2 = of(2021, 6, 5);
        LocalDate end2 = of(2023, 5, 4);
        Period period2 = new Period(start2, end2, "Java Middle", "Spring, Java, SQL, MySQL, JavaScript");
        Company company = new Company("VKontakte", "vk.com");
        company.addPeriod(period1);
        company.addPeriod(period2);

        CompanySection companySection = new CompanySection();
        companySection.addCompany(company);

        resume.addSection(SectionType.EXPERIENCE, companySection);

        Section sectionType = resume.getSectionType(SectionType.EXPERIENCE);

        System.out.println(sectionType);
    }
}
