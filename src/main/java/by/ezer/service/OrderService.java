package by.ezer.service;

import by.ezer.dto.OrderCreateDTO;
import by.ezer.dto.OrderDTO;
import by.ezer.exceptions.DatabaseException;
import by.ezer.models.Order;
import by.ezer.models.Product;
import by.ezer.models.User;
import by.ezer.repositories.OrderRepository;
import by.ezer.repositories.ProductRepository;
import by.ezer.repositories.UserRepository;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    public OrderService(Session session) {
        this.orderRepository = new OrderRepository(session);
        this.userRepository = new UserRepository(session);
        this.productRepository = new ProductRepository(session);
    }

    public OrderDTO createOrder(OrderCreateDTO orderCreateDTO) throws DatabaseException {
        if (orderCreateDTO == null) {
            throw new DatabaseException("OrderCreateDTO cannot be null");
        }
        if (orderCreateDTO.getDate() == null) {
            throw new DatabaseException("Order date cannot be null");
        }
        if (orderCreateDTO.getStatus() == null) {
            throw new DatabaseException("Order status cannot be null");
        }
        User user = userRepository.findById(orderCreateDTO.getUserId());
        if (user == null) {
            throw new DatabaseException("Order with id " + orderCreateDTO.getUserId() + " not found");
        }

        List<Product> products = orderCreateDTO.getProductIds().stream()
                .map(productId -> {
                    try {
                        return productRepository.findById(productId);
                    } catch (DatabaseException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
        if (products.isEmpty() && !orderCreateDTO.getProductIds().isEmpty()) {
            throw new DatabaseException("One or more orders not found");
        }
        Order order = new Order(user, orderCreateDTO.getDate(), orderCreateDTO.getStatus(), products);
        products.forEach(order::addProduct);
        orderRepository.create(order);
        return new OrderDTO(order.getId(),
                order.getUser().getId(),
                order.getDate(),
                order.getStatus(),
                order.getProducts().stream().map(Product::getId).collect(Collectors.toList())
        );
    }

    public OrderDTO getOrderById(Long id) throws DatabaseException {
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new DatabaseException("Order with id " + id + " not found");
        }
        return new OrderDTO(order.getId(),
                order.getUser().getId(),
                order.getDate(),
                order.getStatus(),
                order.getProducts().stream().map(Product::getId).collect(Collectors.toList()));
    }

    public List<OrderDTO> getAllOrders() throws DatabaseException {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> new OrderDTO(order.getId(),
                        order.getUser().getId(),
                        order.getDate(),
                        order.getStatus(),
                        order.getProducts().stream().map(Product::getId).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public void updateOrder(OrderDTO orderDTO) throws DatabaseException {
        if (orderDTO == null || orderDTO.getId() == null) {
            throw new DatabaseException("OrderDTO or ID cannot be null");
        }
        Order existingOrder = orderRepository.findById(orderDTO.getId());
        if (existingOrder == null) {
            throw new DatabaseException("Order with ID " + orderDTO.getId() + " not found");
        }
        User user = userRepository.findById(orderDTO.getUserId());
        if (user == null) {
            throw new DatabaseException("User with id " + orderDTO.getUserId() + " not found");
        }
        existingOrder.setUser(user);
        existingOrder.setDate(orderDTO.getDate());
        existingOrder.setStatus(orderDTO.getStatus());
        if (orderDTO.getProductIds() != null) {
            List<Product> newProducts = orderDTO.getProductIds().stream()
                    .map(productId -> {
                        try {
                            return productRepository.findById(productId);
                        } catch (DatabaseException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
            existingOrder.getProducts().clear();
            newProducts.forEach(existingOrder::addProduct);
        }
        orderRepository.update(existingOrder);
    }

    public void deleteOrder(Long id) throws DatabaseException {
        orderRepository.delete(id);
    }
}
