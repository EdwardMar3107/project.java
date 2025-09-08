package by.ezer.repositories;

import by.ezer.exceptions.DatabaseException;
import by.ezer.models.Order;
import by.ezer.models.Product;
import by.ezer.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class OrderRepository {
    public OrderRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<Order> findAll() throws DatabaseException {
        List<Order> orders = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_SQL)) {

            while (rs.next()) {

                orders.add(mapResultSetToOrder(rs));

            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при выполнении findAll: " + e.getMessage(), e);
        }
        return orders;
    }

    public Order findById(Long id) throws DatabaseException {
        Order order = null;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID_SQL)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    order = mapResultSetToOrder(rs);

                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при выполнении findById: " + e.getMessage(), e);
        }
        return order;
    }

    public void create(Order order) throws DatabaseException {
        try (Connection conn = databaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement orderStmt = conn.prepareStatement(CREATE_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {
                setOrderParameters(orderStmt, order, false);

                orderStmt.executeUpdate();

                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setId(generatedKeys.getLong(1));
                    }
                }

                if (order.getProducts() != null) {
                    try (PreparedStatement mapStmt = conn.prepareStatement(CREATE_MAP_SQL)) {
                        for (Product product : order.getProducts()) {
                            mapStmt.setLong(1, order.getId());
                            mapStmt.setLong(2, product.getId());
                            mapStmt.addBatch();
                        }
                        mapStmt.executeBatch();
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new DatabaseException("Ошибка при выполнении create: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при управлении транзакцией: " + e.getMessage(), e);
        }
    }

    public void update(Order order) throws DatabaseException {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_ORDER_SQL)) {

            setOrderParameters(stmt, order, true);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при выполнении update: " + e.getMessage(), e);
        }
    }

    public void delete (Long id) throws DatabaseException {

        try (Connection conn = databaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement mapStmt = conn.prepareStatement(DELETE_MAP_SQL);
            PreparedStatement orderStmt = conn.prepareStatement(DELETE_ORDER_SQL)) {

                mapStmt.setLong(1, id);
                mapStmt.executeUpdate();

                orderStmt.setLong(1, id);

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new DatabaseException("Ошибка при выполнении delete: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при управлении транзакцией: " + e.getMessage(), e);
        }
    }

    private static final String FIND_ALL_SQL = "SELECT * FROM orders";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM orders WHERE id = ?";
    private static final String CREATE_ORDER_SQL = "INSERT INTO orders (user_id, date, status) VALUES (?, ?, ?)";
    private static final String UPDATE_ORDER_SQL = "UPDATE orders SET user_id = ?, date = ?, status = ? WHERE id = ?";
    private static final String DELETE_ORDER_SQL = "DELETE FROM orders WHERE id = ?";
    private static final String CREATE_MAP_SQL = "INSERT INTO orders_goods_map (order_id, product_id, quantity) VALUES (?, ?, ?)";
    private static final String DELETE_MAP_SQL = "DELETE FROM orders_goods_map WHERE order_id = ?";

    private final DatabaseConnection databaseConnection;

    private void setOrderParameters(PreparedStatement stmt, Order order, boolean includeId) throws SQLException {
        stmt.setLong(1, order.getUserId());
        stmt.setTimestamp(2, Timestamp.valueOf(order.getDate().atStartOfDay()));
        stmt.setString(3, order.getStatus());
        if (includeId) {
            stmt.setLong(4, order.getId());
        }
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order(
                rs.getLong("userId"),
                LocalDate.from(rs.getTimestamp("date").toLocalDateTime()),
                rs.getString("status")
        );
        order.setId(rs.getLong("id"));
        return order;
    }
}
