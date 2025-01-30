package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("PostgreSQL Driver not found", e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transcationalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid =?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            insertContact(conn, r);

            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid=?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            insertSection(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transcationalExecute(conn -> {
           try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES  (?, ?)")) {
               ps.setString(1, r.getUuid());
               ps.setString(2, r.getFullName());
               ps.execute();
           }
           insertContact(conn, r);
           insertSection(conn, r);
           return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transcationalExecute(conn -> {
           Resume resume;
           try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
               ps.setString(1, uuid);
               ResultSet rs = ps.executeQuery();
               if (!rs.next()) {
                   throw new NotExistStorageException(uuid);
               }
               resume = new Resume(uuid, rs.getString("full_name"));
           }

           try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
               ps.setString(1, uuid);
               ResultSet rs = ps.executeQuery();
               while (rs.next()) {
                   addContact(resume, rs);
               }
           }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(resume, rs);
                }
            }

           return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transcationalExecute(conn -> {
           Map<String, Resume> resumes = new LinkedHashMap<>();
           try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r ORDER BY full_name, uuid")) {
               ResultSet rs = ps.executeQuery();
               while (rs.next()) {
                   String uuid = rs.getString("uuid");
                   resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
               }
           }

           try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    addContact(resumes.get(uuid), rs);
                }
           }

           try (PreparedStatement ps = conn.prepareStatement("SELECT * from section")) {
               ResultSet rs = ps.executeQuery();
               while (rs.next()) {
                   String uuid = rs.getString("resume_uuid");
                   addSection(resumes.get(uuid), rs);
               }
           }
           return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    public static void addContact(Resume r, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.addContact(type, value);
        }
    }

    public static void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static void addSection(Resume r, ResultSet rs) throws SQLException {
        String section = rs.getString("section");
        if (section != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            r.addSection(type, JsonParser.read(section, Section.class));
        }
    }

    public static void insertSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, section) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> entry : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                Section section = entry.getValue();
                ps.setString(3, JsonParser.write(section, Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
