package repositories;
import models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseConnection;

public class OrderRepository {

    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        final String sql = "SELECT * FROM orders";

        try (Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Order order = new Order(
                        rs.getLong("user_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("status")
                );
                orders.add(order);
            }

        } catch (SQLException e) {
            System.out.println("Ошибка findAll: " + e.getMessage());
        }
        return orders;

    }

    public Order findById(Long id) {
        final String sql = "SELECT * FROM orders WHERE id = ?";
        Order order = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            stmt.setLong(1, id);
            if (rs.next()) {
                order = new Order(
                        rs.getLong("user_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Ошибка findById: " + e.getMessage());
        }
        return order;
    }

    public void create(Order order) {
        final String sql = "INSERT INTO orders (user_id, date, status) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, order.getUserId());
            stmt.setDate(2, java.sql.Date.valueOf(order.getDate()));
            stmt.setString(3, order.getStatus());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка create: " + e.getMessage());
        }
    }

    public void update (Order order) {

        final String sql = "UPDATE orders SET user_id = ?, date = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt =  conn.prepareStatement(sql)) {

            stmt.setLong(1, order.getId());
            stmt.setLong(2, order.getUserId());
            stmt.setDate(3, java.sql.Date.valueOf(order.getDate()));
            stmt.setString(4, order.getStatus());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка update: " + e.getMessage());
        }
    }

    public void delete (Long id) {

        final String sql = "DELETE FROM orders WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка delete: " + e.getMessage());
        }
    }

}
