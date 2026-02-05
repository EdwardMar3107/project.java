package by.ezer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

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
public class User implements UserDetails {

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

    public User(String userName, int age, String email, String encodedPassword) {
    }

    //Связь User и Order
    //mappedBy - означает foreign key - главный
    //Fetch LAZY - загружать по запросу, а не сразу связанные объекты(для производительности)
    //Cascade - для взаимосвязи, если действие произошло с одной сущностью, значит автоматом и с другой
    //orphanRemoval - если заказ удалили у пользователя, значит удалится из БД
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Order> orders = new ArrayList<>();

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

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinTable(
            name = "user_role_links",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> role.getAuthorities().stream())
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
