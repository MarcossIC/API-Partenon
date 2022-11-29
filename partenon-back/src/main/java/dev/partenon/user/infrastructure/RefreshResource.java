package dev.partenon.user.infrastructure;

import dev.partenon.global.domain.abstractcomponents.query.QueryBus;
import dev.partenon.user.domain.RefreshQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**EndPoint para refrescar los Tokens*/
@RestController
@RequestMapping(path = "/api")
public class RefreshResource {
    @Autowired
    private final QueryBus queryBus;

    public RefreshResource(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    /**
     * Se llama cuando el {@code Access-Token} ha expirado
     * Crea un nuevo {@code Access-Token y Refresh-Token} con el {@code Refresh-Token} anterior
     *
     * @param authorization  Contiene el valor del Header de Authorization
     */
    @GetMapping("/refresh")
    public Mono<ServerResponse>  refreshToken(@RequestHeader(name = AUTHORIZATION) String authorization) throws Exception {
        var query = RefreshQuery.builder()
                .headerToken(authorization)
                .build();

        var tokens = queryBus.ask(query);

        return ServerResponse.ok().bodyValue(tokens);
    }


}
