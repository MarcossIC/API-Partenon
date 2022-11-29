package dev.partenon.user.domain;

import dev.partenon.global.domain.abstractcomponents.query.Query;
import dev.partenon.user.domain.model.TokenModel;
import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Mono;

@Data
@Builder
public class LoginQuery implements Query {
    private String usernameOrEmail;
    private String password;
}
