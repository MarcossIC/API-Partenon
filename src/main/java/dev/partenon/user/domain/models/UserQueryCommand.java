package dev.partenon.user.domain.models;

import dev.partenon.global.domain.ports.command.Command;
import dev.partenon.global.domain.ports.query.Query;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
public final class UserQueryCommand implements Command, Query {
    @NotEmpty(message = "El email no puede estar vacio")
    @Email(message = "El formato del email no es valido")
    private final String email;

    @NotEmpty(message = "La contraseña no puede estar vacia")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z]).*$", message = "La contraseña debe tener almenos un digito y una letra mayuscula")
    @Size(min = 6, max = 20, message = "La contraseña debe de estar entre 6 y 20 caracteres")
    private final String password;

}
