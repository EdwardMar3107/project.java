import exceptions.DatabaseException;
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
    public static void main(String[] args) throws ClassNotFoundException {

        DatabaseConnection databaseConnection = new DatabaseConnection();

        UserRepository userRepository = new UserRepository(databaseConnection);

        try {
            User newUser = new User("Eduard", "Kirov", "edwardmar", "edik200331", LocalDate.of(2002,7,31));

            userRepository.create(newUser);

        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }
}
