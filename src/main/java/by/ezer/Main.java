package by.ezer;

import by.ezer.exceptions.DatabaseException;
import by.ezer.models.Order;
import by.ezer.models.Product;
import by.ezer.models.User;
import by.ezer.repositories.impl.OrderRepositoryImpl;
import by.ezer.repositories.impl.ProductRepositoryImpl;
import by.ezer.repositories.impl.UserRepositoryImpl;
import by.ezer.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, DatabaseException {

    }
}
