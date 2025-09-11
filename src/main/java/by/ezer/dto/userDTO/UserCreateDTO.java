package by.ezer.dto.userDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserCreateDTO {

    private String name;
    private String surname;
    private String login;
    private String password;
    private LocalDate birthDate;

    public UserCreateDTO(String name, String surname, String login, String password, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.birthDate = birthDate;
    }
}
