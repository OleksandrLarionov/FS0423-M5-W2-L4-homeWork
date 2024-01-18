package larionov.API.payloads.authors;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewAuthorDTO(
        @NotEmpty
        @Size(min = 3, max = 30, message = "il nome tra 3 e 30 caratteri")
        String name,
        @NotEmpty
        @Size(min = 3, max = 30, message = "il cognome tra 3 e 30 caratteri")
        String surname,
        @Email(message = "L'indirizzo mail deve essere valido")
        String email,
        LocalDate dataDiNascita,
        String avatar) {

}
