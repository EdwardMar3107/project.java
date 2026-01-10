package by.ezer.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import by.ezer.dto.OrderDTO;
import by.ezer.dto.PagedResult;
import by.ezer.entity.Order;
import by.ezer.entity.Product;
import by.ezer.entity.User;
import by.ezer.mappers.OrderMapper;
import by.ezer.repositories.impl.OrderRepositoryImpl;
import by.ezer.repositories.impl.ProductRepositoryImpl;
import by.ezer.repositories.impl.UserRepositoryImpl;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderService {

    //Добавляем Репы, потому что сервисы работают с ними
    private final OrderRepositoryImpl orderRepository;
    private final UserRepositoryImpl userRepository;
    private final ProductRepositoryImpl productRepository;

    //Маппер для преобразования сущности Order в DTO
    //Выносим маппинг из сервиса для чистоты кода
    private final OrderMapper orderMapper;

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

    public PagedResult<OrderDTO> getAllPaged(int page, int size) {

        PagedResult<Order> result = orderRepository.findAllPaged(page, size);

        List<OrderDTO> dtos = result.getContent().stream()
                .map(orderMapper::toDto)
                .toList();

        return new PagedResult<>(dtos, result.getPage(), result.getSize(), result.getTotalElements());
    }

    public List<OrderDTO> getOrdersByUserEmail(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found" + userEmail));

        List<Order> orders = orderRepository.findByUserId(user.getId());

        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    public void updateOrder(Long orderId, OrderDTO dto) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        order.setTotalAmount(dto.totalAmount());
        order.setOrderDate(dto.orderDate());

        orderRepository.update(order);
    }

    public void deleteOrder(Long orderId) {

        orderRepository.deleteById(orderId);
    }
}
