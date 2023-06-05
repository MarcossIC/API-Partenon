package dev.partenon.user.domain;

import dev.partenon.global.domain.abstractcomponents.query.Query;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class LoginQuery implements Query {
    private final String usernameOrEmail;
    private final String password;
}
