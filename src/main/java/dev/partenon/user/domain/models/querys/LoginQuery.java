package dev.partenon.user.domain.models.querys;

import dev.partenon.global.domain.ports.query.Query;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public final class LoginQuery implements Query {
    @NotEmpty
    private final String email;
    @NotEmpty
    private final String password;
}
