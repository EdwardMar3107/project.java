package by.ezer;

import java.sql.*;

public class Main {

    public static final String CREATE_SQL = "INSERT INTO users (name, age, email) VALUES (?, ?, ?)";
    public static final String SELECT_SQL = "SELECT * FROM users WHERE id = ?";
    public static final String UPDATE_SQL = "UPDATE users SET name = ?, age = ?, email = ? WHERE id = ?";
    public static final String DELETE_SQL = "DELETE FROM users WHERE id = ?";

    public static void main(String[] args) {
        
    }

    public static void create(String name, int age, String email) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, email);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long newId = generatedKeys.getLong(1);
                        System.out.println("User '" + name + "' created with id = " + newId);
                    }
                }
            }
        }  catch (SQLException e) {
            throw new DatabaseException("Failed to create user", e);
        }
    }

    public static void read(long id) {

        try (Connection conn = ConnectionManager.getConnection();

        PreparedStatement ps = conn.prepareStatement(SELECT_SQL)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    long foundId = rs.getLong("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    int age = rs.getInt("age");

                    System.out.println("=== User found ===");
                    System.out.println("ID: " + foundId);
                    System.out.println("Name: " + name);
                    System.out.println("Email: " + email);
                    System.out.println("Age: " + age);
                    System.out.println("==========================");
                } else {
                    System.out.println("User with id = " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error reading user with id = " + id, e);
        }
    }

    public static void update(long id, String newName, int newAge, String newEmail) {

        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (newEmail == null || newEmail.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, newName);
            ps.setInt(2, newAge);
            ps.setString(3, newEmail);
            ps.setLong(4, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User with id = " + id + " updated");
            } else {
                System.out.println("User with id = " + id + " not found. Nothing is updated.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error updating user with id " + id, e);
        }
    }

    public static void delete(long id) {
        try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setLong(1, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User with id = " + id + " deleted");
            } else {
                System.out.println("User with id = " + id + " not found. Nothing is deleted");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting user with id " + id, e);
        }
    }
}
