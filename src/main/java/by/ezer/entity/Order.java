package by.ezer.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//Пояснение в User Entity
@ToString(exclude = "user")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    //Пояснение в User Entity
    @ManyToOne(fetch = FetchType.LAZY)
    //Добавляет колонку для взаимосвязи
    //У пользователя с этим id такой-то заказ
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //Пояснение в User Entity
    //Persist - сохраняем, merge - обновляем
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //Добавляем таблицу для взаимосвязи многих ко многим, где id order && product
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    //Список продуктов в заказе
    private List<Product> products = new ArrayList<>();

    //Пояснение в User Entity
    public Order(LocalDateTime orderDate, BigDecimal totalAmount) {
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.products = new ArrayList<>();
    }

    //Пояснение в User Entity
    public void addProduct(Product product) {
        products.add(product);
        product.getOrders().add(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.getOrders().remove(this);
    }
}
