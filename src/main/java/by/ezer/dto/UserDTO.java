package by.ezer.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Builder
@Accessors(chain = true)
public record UserDTO(Long id, String name, Integer age, String email) {
}
