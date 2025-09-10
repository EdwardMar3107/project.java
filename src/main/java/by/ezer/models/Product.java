package by.ezer.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "products")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "name")
    private String name;

    @Column (name = "price")
    private BigDecimal price;

    @Column (name = "is_available")
    private Boolean isAvailable;

    @Column (name = "created_at")
    private LocalDate createdAt;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders = new ArrayList<>();

    public Product (String name, BigDecimal price, Boolean isAvailable, LocalDate createdAt) {
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
        this.createdAt = createdAt;
    }

    public void addOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");
        this.orders.add(order);
        order.addProduct(this);
    }
}
