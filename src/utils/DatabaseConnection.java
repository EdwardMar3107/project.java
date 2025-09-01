package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String userDb = "postgres";
    private static final String password = "postgres";
    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, userDb, password);
    }
}
