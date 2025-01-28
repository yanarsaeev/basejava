package com.urise.webapp.storage;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.model.ContactType.*;
import static java.time.LocalDate.*;

public class ResumeTestData {
    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(SKYPE, "qwertt1234");
        resume.addContact(LINKEDIN, "qwerty");
        resume.addContact(PHONE, "89990990909");
        resume.addContact(MAIL, "qweerty@gmail.com");
        resume.addContact(GITHUB, "qwertycode");
        resume.addContact(STACKOVERFLOW, "qwertystack");

        resume.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по " +
                "Java Web и Enterprise технологиям"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры."));
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("Организация команды и успешная реализация Java проектов " +
                "для сторонних заказчиков", "разработка проектов \"Разработка Web приложения\",\"Java Enterprise\""));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts", "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy"));

        return resume;
    }
}
