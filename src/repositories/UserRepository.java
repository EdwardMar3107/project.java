package repositories;

import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseConnection;
import exceptions.DatabaseException;

public class UserRepository {

    public UserRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<User> findAll() throws DatabaseException {
        List<User> users = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_SQL)) {

            while (rs.next()) {

                users.add(mapResultSetToUser(rs));

            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при выполнении findAll: " + e.getMessage(), e);
        }
         return users;
    }


    public User findById(Long id) throws DatabaseException {
        User user = null;

        try (Connection conn = databaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID_SQL)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    user = mapResultSetToUser(rs);

                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при выполнении findById: " + e.getMessage(), e);
        }
        return user;
    }

    public void create (User user) throws DatabaseException {

        try (Connection conn = databaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(CREATE_SQL)) {

            setUserParameters(stmt, user, false);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при выполнении create: " + e.getMessage(), e);
        }
    }

    public void update (User user) throws DatabaseException {

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            setUserParameters(stmt, user, true);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при выполнении update: " + e.getMessage(), e);
        }
    }

    public void delete(Long id) throws DatabaseException {

        try (Connection conn = databaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при выполнении delete: " + e.getMessage(), e);
        }
    }

    private static final String FIND_ALL_SQL = "SELECT * FROM users";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
    private static final String CREATE_SQL = "INSERT INTO users (name, surname, login, password, birth_date) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE users SET name = ?, surname = ?, login = ?, password = ?, birth_date = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM users WHERE id = ?";

    private final DatabaseConnection databaseConnection;

    private void setUserParameters (PreparedStatement stmt, User user, boolean includeId) throws SQLException {
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getSurname());
        stmt.setString(3, user.getLogin());
        stmt.setString(4, user.getPassword());
        stmt.setDate(5, Date.valueOf(user.getBirthDate()));
        if (includeId) {
            stmt.setLong(6, user.getId());
        }
    }

    private User mapResultSetToUser (ResultSet rs) throws SQLException {
        User user = new User (
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("login"),
                rs.getString("password"),
                rs.getDate("birth_date").toLocalDate()
        );
        user.setId(rs.getLong("id"));
        return user;
    }
}
