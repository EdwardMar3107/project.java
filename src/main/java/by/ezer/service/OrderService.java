package by.ezer.service;

import by.ezer.dto.OrderDTO;
import by.ezer.dto.ProductDTO;
import by.ezer.entity.Order;
import by.ezer.entity.Product;
import by.ezer.entity.User;
import by.ezer.repository.OrderRepository;
import by.ezer.repository.ProductRepository;
import by.ezer.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class OrderService {

    //Добавляем Репы, потому что сервисы работают с ними
    private final OrderRepository orderRepository = new OrderRepository();
    private final UserRepository userRepository = new UserRepository();
    private final ProductRepository productRepository =new ProductRepository();

    //Создаем метод: Создания Заказа
    public OrderDTO createOrder(String userEmail, List<Long> productIds) {

        //Находим пользователей по почте
            Optional<User> userOpt = userRepository.findByEmail(userEmail);
            //Возвращаем пользователя есть таковой имеется, в ином случае исключение
            User user = userOpt.orElseThrow(() -> new RuntimeException("User not found" + userEmail));

            //Создаем переменную, которая будет хранить сумму заказов, а также список продуктов
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Product> products = new ArrayList<>();

        //С помощью циклам проходимся по заказам и добавляем их в total, в ином случаем - исключение
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

        //Преобразуем всё в DTO, чтобы создать "картинку"
        return OrderDTO.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .userName(user.getUsername())
                .userEmail(user.getEmail())
                .products(products.stream()
                        .map(p -> ProductDTO.builder()
                                .id(p.getId())
                                .name(p.getProductName())
                                .price(p.getPrice())
                                .description(p.getDescription())
                                .build())
                        .toList())
                .build();
    }
}
