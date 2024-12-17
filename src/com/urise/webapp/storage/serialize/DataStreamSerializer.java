package com.urise.webapp.storage.serialize;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                switch (entry.getKey()) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) entry.getValue()).getContent());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        int size = (((ListSection) entry.getValue()).getStrings().size());
                        for (int i = 0; i < size; i++) {
                            dos.writeUTF(entry.getValue().toString());
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Company> companyList = ((CompanySection) entry.getValue()).getCompanies();
                        for (Company company : companyList) {
                            dos.writeUTF(company.getName());
                            dos.writeUTF(company.getWebsite());
                            List<Period> periodList = company.getPeriods();
                            for (Period period : periodList) {
                                writeLocalDate(dos, period.getStart());
                                writeLocalDate(dos, period.getEnd());
                                dos.writeUTF(period.getDescription());
                                dos.writeUTF(period.getTitle());
                            }
                        }
                    }
                }
            }
        }
    }

    public void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactSize = dis.readInt();
            for (int i = 0; i < contactSize; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionSize = dis.readInt();
            for (int i = 0; i < sectionSize; i++) {
                resume.addSection(SectionType.valueOf(dis.readUTF()), readSection(dis, SectionType.valueOf(dis.readUTF())));
            }
            return resume;
        }
    }

    public Section readSection(DataInputStream dis, SectionType type) throws IOException {
        switch (type) {
            case OBJECTIVE, PERSONAL -> {
                return new TextSection(dis.readUTF());
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                int size = dis.readInt();
                List<String> list = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    list.add(dis.readUTF());
                }
                return new ListSection(list);
            }
            case EXPERIENCE, EDUCATION -> {
                CompanySection companySection = new CompanySection();
                int size = dis.readInt();
                for (int i = 0; i < size; i++) {
                    companySection.addCompany(new Company(dis.readUTF(), dis.readUTF()));
                }
                return companySection;
            }
            default -> throw new IllegalStateException();
        }
    }
}
