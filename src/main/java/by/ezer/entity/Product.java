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
    private String productName;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
    @Column(nullable = false, length = 2000)
    private String description;

    //Пояснение в User Entity
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    //Пояснение в User Entity
    public Product(String productName, BigDecimal price, String description) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.orders = new ArrayList<>();
    }
}
