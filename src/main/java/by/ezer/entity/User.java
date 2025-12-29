package by.ezer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", nullable = false)
    private String username;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Order> orders = new ArrayList<>();

    public User(String username, int age, String email) {
        this.username = username;
        this.age = age;
        this.email = email;
        this.orders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setUser(null);
    }
}
