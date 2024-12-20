package com.urise.webapp.storage.serialize;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    public interface ElementReader {
        void read() throws IOException;
    }

    public interface ListReader<T> {
        T read() throws IOException;
    }

    public interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    public <T> void writeElement(DataOutputStream dos, Collection<T> collection,
                                 ElementWriter<T> contactWriter) throws IOException {
        dos.writeInt(collection.size());
        for (T elem : collection) {
            contactWriter.write(elem);
        }
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeElement(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = r.getSections();
            writeElement(dos, sections.entrySet(), entry -> {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(type.name());

                switch (type) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) section).getContent());
                    case ACHIEVEMENT, QUALIFICATIONS -> writeElement(dos, ((ListSection) section).getStrings(), dos::writeUTF);
                    case EXPERIENCE, EDUCATION -> writeElement(dos, ((CompanySection) section).getCompanies(), company -> {
                        dos.writeUTF(company.getName());
                        dos.writeUTF(company.getWebsite());
                        writeElement(dos, company.getPeriods(), period -> {
                            writeLocalDate(dos, period.getStart());
                            writeLocalDate(dos, period.getEnd());
                            dos.writeUTF(period.getTitle());
                            dos.writeUTF(period.getDescription());
                        });
                    });
                }
            });
        }
    }

    public void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
    }

    public LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readElement(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readElement(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                resume.addSection(type, readSection(dis, type));
            });

            return resume;
        }
    }

    public void readElement(DataInputStream dis, ElementReader elementReader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            elementReader.read();
        }
    }

    public <T> List<T> readList(DataInputStream dis, ListReader<T> listReader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(listReader.read());
        }
        return list;
    }

    public Section readSection(DataInputStream dis, SectionType type) throws IOException {
        switch (type) {
            case OBJECTIVE, PERSONAL -> {
                return new TextSection(dis.readUTF());
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                return new ListSection(readList(dis, dis::readUTF));
            }
            case EXPERIENCE, EDUCATION -> {
                return new CompanySection(readList(dis, () -> new Company(
                        readList(dis, () -> new Period(readLocalDate(dis), readLocalDate(dis),
                                dis.readUTF(), dis.readUTF()))
                )));
            }
            default -> throw new IllegalStateException();
        }
    }
}
