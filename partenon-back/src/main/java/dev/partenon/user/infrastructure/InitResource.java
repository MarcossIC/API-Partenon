package dev.partenon.user.infrastructure;

import dev.partenon.global.domain.abstractcomponents.query.QueryBus;
import dev.partenon.user.domain.InitQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**Esto es cuando un usuario "ADMIN" ha salido de la pagina y ha dejado su sesión abierta*/
@RestController
@RequestMapping("/api/auth")
public class InitResource {
    @Autowired
   private QueryBus queryBus;

    /**
     * Regresa el ID del museo del dueño del token
     * Verifica que el token no haya expirado
     *
     * @param authorization  Contiene el valor del Header de Authorization
     * @throws IOException
     */
    @GetMapping("/init")
    public Mono<ServerResponse>  initApp(@RequestHeader(name = AUTHORIZATION) String authorization) throws Exception {
        final var query = InitQuery.builder()
                .authToken(authorization)
                .build();

        var response = queryBus.ask(query);

        return ServerResponse.ok().bodyValue(response);
    }


}
