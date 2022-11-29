package dev.partenon.user.application.handlers;

import dev.partenon.global.domain.abstractcomponents.query.QueryHandler;
import dev.partenon.security.infrastructure.components.JWTProvider;
import dev.partenon.security.infrastructure.components.SignInService;
import dev.partenon.user.application.UserRepository;
import dev.partenon.user.domain.model.TokenModel;
import dev.partenon.user.domain.LoginQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**Maneja la query para Login Resource*/
@Service
@RequiredArgsConstructor
public class LoginQueryHandler implements QueryHandler<LoginQuery, TokenModel> {
    @Autowired
    private final SignInService service;

    @Override
    public TokenModel handle(LoginQuery query) throws Exception {
        return service.signIn(query.getUsernameOrEmail(),query.getPassword()).block();
    }
}
