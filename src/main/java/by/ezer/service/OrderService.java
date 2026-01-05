package by.ezer.service;

import by.ezer.dto.OrderDTO;
import by.ezer.dto.ProductDTO;
import by.ezer.entity.Order;
import by.ezer.entity.Product;
import by.ezer.entity.User;
import by.ezer.mappers.api.OrderMapper;
import by.ezer.mappers.api.ProductMapper;
import by.ezer.mappers.impl.OrderMapperImpl;
import by.ezer.mappers.impl.ProductMapperImpl;
import by.ezer.repositories.impl.OrderRepositoryImpl;
import by.ezer.repositories.impl.ProductRepositoryImpl;
import by.ezer.repositories.impl.UserRepositoryImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class OrderService {

    //Добавляем Репы, потому что сервисы работают с ними
    private final OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();
    private final UserRepositoryImpl userRepository = new UserRepositoryImpl();
    private final ProductRepositoryImpl productRepository =new ProductRepositoryImpl();

    //Маппер для преобразования сущности Order в DTO
    //Выносим маппинг из сервиса для чистоты кода
    private final OrderMapper orderMapper;

    //Ручная инициализация мапперов (вручную создаём зависимости)
    public OrderService() {
        ProductMapper productMapper = new ProductMapperImpl();
        this.orderMapper = new OrderMapperImpl(productMapper);
    }

    //Создаем метод: Создание Заказа
    public OrderDTO createOrder(String userEmail, List<Long> productIds) {

        //Находим пользователей по почте
            Optional<User> userOpt = userRepository.findByEmail(userEmail);
            //Возвращаем пользователя если таковой имеется, в ином случае исключение
            User user = userOpt.orElseThrow(() -> new RuntimeException("User not found" + userEmail));

            //Создаем переменную, которая будет хранить сумму заказов, а также список продуктов
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Product> products = new ArrayList<>();

        //С помощью цикла проходимся по заказам и добавляем их в total, в ином случаем - исключение, если их нет
        for (Long productId : productIds) {

            Optional<Product> productOpt = productRepository.findById(productId);
            Product product = productOpt.orElseThrow(() -> new RuntimeException("Product not found"  + productId));

            products.add(product);
            totalPrice = totalPrice.add(product.getPrice());
        }

        //Создаем заказ и добавляем туда заказ
        Order order = new Order(LocalDateTime.now(), totalPrice);
        for (Product product : products) {
            order.addProduct(product);
        }
        //Привязываем заказ к пользователю
        user.addOrder(order);
        //Сохраняем всё дело
        userRepository.save(user);

        //Преобразуем всё в DTO, чтобы создать "картинку", используем маппер
        return orderMapper.toDto(order);
    }

    public OrderDTO getOrderById(Long id) {

        Optional<Order> orderOpt = orderRepository.findByIdWithDetails(id);
        Order order = orderOpt.orElseThrow(() -> new RuntimeException("Order not found" + id));

        //Используем маппер
        return orderMapper.toDto(order);
    }
}
