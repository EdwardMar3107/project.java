package by.ezer.service;

import by.ezer.dto.OrderCreateDTO;
import by.ezer.exceptions.ServiceException;
import by.ezer.repositories.api.OrderRepository;
import by.ezer.repositories.api.ProductRepository;
import by.ezer.repositories.api.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import by.ezer.dto.OrderDTO;
import by.ezer.entity.Order;
import by.ezer.entity.Product;
import by.ezer.entity.User;
import by.ezer.mappers.OrderMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    //Добавляем Репы, потому что сервисы работают с ними
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    //Маппер для преобразования сущности Order в DTO
    //Выносим маппинг из сервиса для чистоты кода
    private final OrderMapper orderMapper;

    //Создаем метод: Создание Заказа
    @Transactional
    public OrderDTO createOrder(OrderCreateDTO request) {
        try {
            //Находим пользователей по почте
            Optional<User> userOpt = userRepository.findById(request.userId());
            //Возвращаем пользователя если таковой имеется, в ином случае исключение
            User user = userOpt.orElseThrow(() -> new RuntimeException("User not found" + request.userId()));

            //Создаем переменную, которая будет хранить сумму заказов, а также список продуктов
            BigDecimal totalPrice = BigDecimal.ZERO;
            List<Product> products = new ArrayList<>();

            //С помощью цикла проходимся по заказам и добавляем их в total, в ином случаем - исключение, если их нет
            for (Long productId : request.productIds()) {

                Optional<Product> productOpt = productRepository.findById(productId);
                Product product = productOpt.orElseThrow(() -> new RuntimeException("Product not found"  + productId));

                products.add(product);
                totalPrice = totalPrice.add(product.getPrice());
            }

            //Создаем заказ и добавляем туда заказ
            Order order = new Order(LocalDateTime.now(), totalPrice);
            order.setProducts(new ArrayList<>());
            for (Product product : products) {
                order.addProduct(product);
            }
            //Привязываем заказ к пользователю
            user.addOrder(order);
            //Сохраняем всё дело
            orderRepository.save(order);

            //Преобразуем всё в DTO, чтобы создать "картинку", используем маппер
            return orderMapper.toDto(order);
        } catch (RuntimeException e) {
            throw new ServiceException("Cannot save order in service", HttpStatus.BAD_REQUEST);
        }
    }

//    public Optional<OrderDTO> findById(Long id) {
//        Optional<Order> orderOpt = orderRepository.findById(id);
//        Order order = orderOpt.orElseThrow(() -> new RuntimeException("Order not found" + id));
//
//        //Используем маппер
//        return orderRepository.findById(id).map(orderMapper::toDto);
//    }
//
//    public PagedResult<OrderDTO> findAllPaged(int page, int size) {
//        PagedResult<Order> result = orderRepository.findAllPaged(page, size);
//
//        List<OrderDTO> dtos = result.getContent().stream()
//                .map(orderMapper::toDto)
//                .toList();
//
//        return new PagedResult<>(dtos, result.getPage(), result.getSize(), result.getTotalElements());
//    }
//
//    @Transactional
//    public void updateOrder(Long orderId, OrderDTO dto) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
//
//        order.setTotalAmount(dto.totalAmount());
//        order.setOrderDate(dto.orderDate());
//
//        orderRepository.update(order);
//    }
//
//    @Transactional
//    public void deleteOrder(Long orderId) {
//        orderRepository.deleteById(orderId);
//    }
}
