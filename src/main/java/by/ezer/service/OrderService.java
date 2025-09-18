package by.ezer.service;

import by.ezer.dto.orderDTO.OrderCreateDTO;
import by.ezer.dto.orderDTO.OrderDTO;
import by.ezer.exceptions.RepositoryException;
import by.ezer.mappers.OrderMapper;
import by.ezer.models.Order;
import by.ezer.models.Product;
import by.ezer.models.User;
import by.ezer.repositories.api.OrderRepository;
import by.ezer.repositories.api.ProductRepository;
import by.ezer.repositories.api.UserRepository;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    public OrderService(Session session) {
        this.orderRepository = new by.ezer.repositories.impl.OrderRepositoryImpl(session);
        this.userRepository = new by.ezer.repositories.impl.UserRepositoryImpl(session);
        this.productRepository = new by.ezer.repositories.impl.ProductRepositoryImpl(session);
    }

    public OrderDTO createOrder(OrderCreateDTO orderCreateDTO) throws RepositoryException {
        if (orderCreateDTO == null) {
            throw new RepositoryException("OrderCreateDTO cannot be null");
        }
        if (orderCreateDTO.getDate() == null) {
            throw new RepositoryException("Order date cannot be null");
        }
        if (orderCreateDTO.getStatus() == null) {
            throw new RepositoryException("Order status cannot be null");
        }
        User user = userRepository.findById(orderCreateDTO.getUserId());
        if (user == null) {
            throw new RepositoryException("User with id " + orderCreateDTO.getUserId() + " not found");
        }

        List<Product> products = orderCreateDTO.getProductIds().stream()
                .map(productId -> {
                    try {
                        return productRepository.findById(productId);
                    } catch (RepositoryException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
        if (products.isEmpty() && !orderCreateDTO.getProductIds().isEmpty()) {
            throw new RepositoryException("One or more orders not found");
        }

        Order order = OrderMapper.INSTANCE.toEntity(orderCreateDTO);
        order.setUser(user);
        order.setProducts(new HashSet<>(products));

        orderRepository.create(order);
        return OrderMapper.INSTANCE.toDTO(order);
    }

    public OrderDTO getOrderById(Long id) throws RepositoryException {
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new RepositoryException("Order with id " + id + " not found");
        }
        return OrderMapper.INSTANCE.toDTO(order);
    }

    public List<OrderDTO> getAllOrders() throws RepositoryException {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public void updateOrder(OrderDTO orderDTO) throws RepositoryException {
        if (orderDTO == null || orderDTO.getId() == null) {
            throw new RepositoryException("OrderDTO or ID cannot be null");
        }
        Order existingOrder = orderRepository.findById(orderDTO.getId());
        if (existingOrder == null) {
            throw new RepositoryException("Order with ID " + orderDTO.getId() + " not found");
        }
        User user = userRepository.findById(orderDTO.getUserId());
        if (user == null) {
            throw new RepositoryException("User with id " + orderDTO.getUserId() + " not found");
        }

        OrderMapper.INSTANCE.updateOrderFromDTO(orderDTO, existingOrder); // Обновляем поля
        existingOrder.setUser(user);

        if (orderDTO.getProductIds() != null) {
            List<Product> newProducts = orderDTO.getProductIds().stream()
                    .map(productId -> {
                        try {
                            return productRepository.findById(productId);
                        } catch (RepositoryException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
            existingOrder.getProducts().clear();
        }
        orderRepository.update(existingOrder);
    }

    public void deleteOrder(Long id) throws RepositoryException {
        orderRepository.delete(id);
    }
}
