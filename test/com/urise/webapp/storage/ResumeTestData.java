package com.urise.webapp.storage;

import com.urise.webapp.model.*;

import java.time.LocalDate;

import static java.time.LocalDate.*;

public class ResumeTestData {
    public static Resume createResume(String fullName, String uuid) {
        Resume resume = new Resume(uuid, fullName);
        TextSection textSection = new TextSection("Ведущий стажировок и корпоративного обучения по " +
                "Java Web и Enterprise технологиям");
        TextSection textSection1 = new TextSection("Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры.");

        ListSection listSection = new ListSection();
        listSection.addString("Организация команды и успешная реализация Java проектов для сторонних заказчиков");
        listSection.addString("разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"");

        ListSection listSection1 = new ListSection();
        listSection1.addString("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        listSection1.addString("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        listSection1.addString("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");


        LocalDate start = of(2020, 6, 11);
        LocalDate end = of(2021, 5, 5);
        Period period = new Period(start, end, "Java Developer", "Spring, Java, SQL");
        Company company = new Company("VKontakte", "vk.com");
        company.addPeriod(period);

        CompanySection companySection = new CompanySection();
        companySection.addCompany(company);

        resume.addSection(SectionType.OBJECTIVE, textSection);
        resume.addSection(SectionType.PERSONAL, textSection1);
        resume.addSection(SectionType.ACHIEVEMENT, listSection);
        resume.addSection(SectionType.QUALIFICATIONS,listSection1);
        resume.addSection(SectionType.EXPERIENCE, companySection);

        return resume;
    }
}
