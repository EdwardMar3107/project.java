package repositories;
import models.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseConnection;

import javax.xml.crypto.Data;

public class ProductRepository {

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        final String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getBoolean("is_available"),
                        rs.getDate("created_at").toLocalDate()
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка в findAll: " + e.getMessage());
        }
        return products;
    }

    public Product findById(Long id) {
        final String sql = "SELECT * FROM products WHERE id = ?";
        Product product = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            stmt.setLong(1, id);
            if (rs.next()) {
                product = new Product(
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getBoolean("is_available"),
                        rs.getDate("created_at").toLocalDate()
                );
            }
        } catch (SQLException e) {
            System.out.println("Ошибка в findById: " + e.getMessage());
        }
        return product;
    }

    public void create(Product product) {
        final String sql = "INSERT INTO products (name, price, is_available, created_at) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getName());
            stmt.setBigDecimal(2, product.getPrice());
            stmt.setBoolean(3, product.getAvailable());
            stmt.setDate(4, java.sql.Date.valueOf(product.getCreatedAt()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка в create: " + e.getMessage());
        }
    }

    public void update(Product product) {
        final String sql = "UPDATE products SET name = ?, price = ?, is_available = ?, created_at = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setBigDecimal(2, product.getPrice());
            stmt.setBoolean(3, product.getAvailable());
            stmt.setDate(4, java.sql.Date.valueOf(product.getCreatedAt()));
            stmt.setLong(5, product.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка в update: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        final String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка в delete: " + e.getMessage());
        }
    }
}



