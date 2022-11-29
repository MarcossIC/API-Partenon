package dev.partenon.user.infrastructure;

import dev.partenon.global.domain.abstractcomponents.query.QueryBus;
import dev.partenon.user.domain.model.AuthUserRestModel;
import dev.partenon.user.domain.LoginQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**Endpoint para hacer Log-in*/
@RestController
@RequestMapping("/api/auth")
public class LoginResource {
    @Autowired
    private QueryBus queryBus;

    @PostMapping("/login")
    public Mono<ServerResponse> login(@RequestBody @Valid AuthUserRestModel authUserRestModel) throws Exception {
        var query = LoginQuery.builder()
                .usernameOrEmail(authUserRestModel.getUsernameOrEmail())
                .password(authUserRestModel.getPassword())
                .build();

        var response =queryBus.ask(query);

        return response == null ? ServerResponse.ok().bodyValue(response) : ServerResponse.badRequest().build();
    }
}
