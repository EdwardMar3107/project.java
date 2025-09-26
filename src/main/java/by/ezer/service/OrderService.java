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
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderDTO save(OrderCreateDTO orderCreateDTO) throws RepositoryException {
        User user = userRepository.findById(orderCreateDTO.getUserId());
        if (user == null) {
            throw new RepositoryException("User with id " + orderCreateDTO.getUserId() + " not found");
        }

        Order order = orderMapper.toEntity(orderCreateDTO, productRepository, userRepository);
        Set<Product> products = order.getProducts();
        if (products == null || (products.isEmpty() && orderCreateDTO.getProductIds() != null && !orderCreateDTO.getProductIds().isEmpty())) {
            throw new RepositoryException("One or more products not found");
        }

        orderRepository.create(order);
        return orderMapper.toDTO(order);
    }

    public OrderDTO findById(Long id) throws RepositoryException {
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new RepositoryException("Order with id " + id + " not found");
        }
        return orderMapper.toDTO(order);
    }

    public List<OrderDTO> findAll() throws RepositoryException {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public void update(OrderDTO orderDTO) throws RepositoryException {
        Order existingOrder = orderRepository.findById(orderDTO.getId());
        if (existingOrder == null) {
            throw new RepositoryException("Order with id " + orderDTO.getId() + " not found");
        }
        User user = userRepository.findById(orderDTO.getUserId());
        if (user == null) {
            throw new RepositoryException("User with id " + orderDTO.getUserId() + " not found");
        }

        orderMapper.updateOrderFromDTO(orderDTO, existingOrder, productRepository);
        existingOrder.setUser(user);

        orderRepository.update(existingOrder);
    }

    public void delete(Long id) throws RepositoryException {
        orderRepository.delete(id);
    }
}
