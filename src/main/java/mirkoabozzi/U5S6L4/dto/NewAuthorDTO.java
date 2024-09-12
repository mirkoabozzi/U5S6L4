package mirkoabozzi.U5S6L4.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewAuthorDTO(
        @NotEmpty(message = "Name is required")
        String name,
        @NotEmpty(message = "Surname is required")
        String surname,
        @NotEmpty(message = "Email is required")
        @Email(message = "Email not valid")
        String email,
        @NotNull(message = "Birthdate is required")
        LocalDate birthDate) {
}
