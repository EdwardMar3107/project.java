package by.ezer.dto.userDTO;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserCreateDTO {
    private String name;
    private String surname;
    private String login;
    private String password;
    private LocalDate birthDate;
}
