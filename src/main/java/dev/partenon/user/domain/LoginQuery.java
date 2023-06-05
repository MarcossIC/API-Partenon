package dev.partenon.user.domain;

import dev.partenon.global.domain.abstractcomponents.query.Query;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginQuery implements Query {
    private String usernameOrEmail;
    private String password;
}
