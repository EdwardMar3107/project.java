import exceptions.DatabaseException;
import models.Order;
import models.Product;
import models.User;
import repositories.OrderRepository;
import repositories.ProductRepository;
import repositories.UserRepository;
import utils.DatabaseConnection;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, DatabaseException {

        DatabaseConnection databaseConnection = new DatabaseConnection();

        OrderRepository orderRepository = new OrderRepository(databaseConnection);

        ProductRepository productRepository = new ProductRepository(databaseConnection);

        Order byId = orderRepository.findById(1L);

        System.out.println(byId);
    }
}
