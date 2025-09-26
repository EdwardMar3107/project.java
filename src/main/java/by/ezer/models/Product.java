package by.ezer.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table (name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
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
    private Set<Order> orders = new HashSet<>();
}
