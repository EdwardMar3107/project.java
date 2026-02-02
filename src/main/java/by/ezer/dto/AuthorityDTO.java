package by.ezer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AuthorityDTO {
    @NotNull
    private Long id;

    @NotEmpty(message = "Name can't be empty")
    @Size(min = 6, max = 32, message = "Name cannot be lower than 6 characters and bigger than 32 characters")
    private String name;
}
