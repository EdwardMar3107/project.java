import models.Product;
import models.User;
import repositories.OrderRepository;
import repositories.ProductRepository;
import repositories.UserRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        ProductRepository productRepo = new ProductRepository();
        UserRepository userRepo = new UserRepository();
        OrderRepository orderRepo = new OrderRepository();

    }
}
