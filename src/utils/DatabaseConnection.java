package utils;

import exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String userDb = "postgres";
    private static final String password = "postgres";

    public Connection getConnection() throws DatabaseException {
        try {
            return DriverManager.getConnection(url, userDb, password);
        } catch (SQLException e) {
            throw new DatabaseException("Не удалось установить соединение с базой данных: " + e.getMessage(), e);
        }
    }
}
