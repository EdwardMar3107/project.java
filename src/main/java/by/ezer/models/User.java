package by.ezer.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column (name = "name", nullable = false)
    private String name;

    @Column (name = "surname", nullable = false)
    private String surname;

    @Column (name = "login", nullable = false, unique = true)
    private String login;

    @Column (name = "password", nullable = false)
    private String password;

    @Column (name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public User(String name, String surname, String login, String password, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.birthDate = birthDate;
    }
}
