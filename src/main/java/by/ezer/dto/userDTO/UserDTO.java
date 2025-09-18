package by.ezer.dto.userDTO;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
}
