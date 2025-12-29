package by.ezer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name", nullable = false, length = 255)
    private String productname;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
    @Column(nullable = false, length = 2000)
    private String description;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public Product(String productname, BigDecimal price, String description) {
        this.productname = productname;
        this.price = price;
        this.description = description;
        this.orders = new ArrayList<>();
    }
}
