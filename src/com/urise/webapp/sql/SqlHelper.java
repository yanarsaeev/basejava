package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String comm, SqlExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(comm)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw new ExistStorageException(comm);
        }
    }

    public void execute(String comm) {
        execute(comm, PreparedStatement::execute);
    }
}
