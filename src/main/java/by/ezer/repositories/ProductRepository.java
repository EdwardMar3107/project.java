package by.ezer.repositories;
import models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseConnection;
import exceptions.DatabaseException;

public class ProductRepository {
    public ProductRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<Product> findAll() throws DatabaseException {
        List<Product> products = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_SQL)) {

            while (rs.next()) {

                products.add(mapResultSetToProduct(rs));

            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при выполнении findAll: " + e.getMessage(), e);
        }
        return products;
    }

    public Product findById(Long id) throws DatabaseException {
        Product product = null;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID_SQL)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    product = mapResultSetToProduct(rs);

                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при выполнении findById: " + e.getMessage(), e);
        }
        return product;
    }

    public void create(Product product) throws DatabaseException {

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            setProductParameters(stmt, product, false);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при выполнении create: " + e.getMessage(), e);
        }
    }

    public void update(Product product) throws DatabaseException {

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            setProductParameters(stmt, product, true);
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

    private static final String FIND_ALL_SQL = "SELECT * FROM products";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM products WHERE id = ?";
    private static final String CREATE_SQL = "INSERT INTO products (name, price, is_available, created_at) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE products SET name = ?, price = ?, is_available = ?, created_at = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM products WHERE id = ?";

    private final DatabaseConnection databaseConnection;

    private void setProductParameters(PreparedStatement stmt, Product product, boolean includeId) throws SQLException {
        stmt.setString(1, product.getName());
        stmt.setBigDecimal(2, product.getPrice());
        stmt.setBoolean(3, product.getAvailable());
        stmt.setDate(4, Date.valueOf(product.getCreatedAt()));
        if (includeId) {
            stmt.setLong(5, product.getId());
        }
    }

    private Product mapResultSetToProduct (ResultSet rs) throws  SQLException {
        Product product = new Product(
                rs.getString("name"),
                rs.getBigDecimal("price"),
                rs.getBoolean("isAvailable"),
                rs.getDate("createdAt").toLocalDate()
        );
        product.setId(rs.getLong("id"));
        return product;
    }
}



