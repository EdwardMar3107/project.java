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
//exclude нужен, чтобы избежать рекурсии и LazyInitializationException при печати
//Рекурсия в Java — это приём программирования, при котором метод вызывает сам себя для решения задачи.
//Рекурсивные решения особенно удобны в случаях, когда задачу можно разбить на несколько однотипных подзадач меньшего размера.
@ToString(exclude = "orders")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    //Связь User и Order
    //mappedBy - означает foreign key - главный
    //Fetch LAZY - загружать по запросу, а не сразу связанные объекты(для производительности)
    //Cascade - для взаимосвязи, если действие произошло с одной сущностью, значит автоматом и с другой
    //orphanRemoval - если заказ удалили у пользователя, значит удалится из БД
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Order> orders = new ArrayList<>();

    //Учитывая что есть аннотация lombok, всё равно для удобства нужно прописать конструктор
    //id генерируется автоматически с автоинкрементом
    public User(String userName, int age, String email, String password, Role role) {
        this.userName = userName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.role = role;
        this.orders = new ArrayList<>();
    }

    //Метод, который позволяет создать связь
    //Синхронизирует обе стороны связи: добавляет заказ в список пользователя
    public void addOrder(Order order) {
        //Добавить заказ
        orders.add(order);
        //Связать ЭТОГО пользователя с заказом
        //И устанавливает пользователя у заказа
        order.setUser(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setUser(null);
    }
}
