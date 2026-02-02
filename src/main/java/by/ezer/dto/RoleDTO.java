package by.ezer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RoleDTO {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 2, max = 16, message = "This role should be in diapazon from 2 to 16 characters")
    private String name;
}
