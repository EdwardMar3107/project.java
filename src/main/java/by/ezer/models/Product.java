package by.ezer.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    public Product (String name, BigDecimal price, Boolean isAvailable, LocalDate createdAt) {
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
        this.createdAt = createdAt;
    }
}
