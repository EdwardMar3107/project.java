package repositories;

import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseConnection;

public class UserRepository {

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        final String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User (
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getDate("birth_date").toLocalDate()
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
         return users;
    }


    public User findById(Long id) {
        final String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User (
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getDate("birth_date").toLocalDate()
                );
            }
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return user;
    }

    public void create (User user) {
        final String sql = "INSERT INTO users (name, surname, login, password, birth_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getLogin());
            stmt.setString(4, user.getPassword());
            stmt.setDate(5, Date.valueOf(user.getBirthDate()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

    }

    public void update (User user) {
        final String sql = "UPDATE users SET name = ?, surname = ?, login = ?, password = ?, birth_date = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getSurname());
            stmt.setString(4, user.getLogin());
            stmt.setString(5, user.getPassword());
            stmt.setDate(6, Date.valueOf(user.getBirthDate()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

    }

    public void delete(Long id) {
        final String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
