package dev.partenon.user.infrastructure.web;

import dev.partenon.global.domain.abstractcomponents.query.QueryBus;
import dev.partenon.user.domain.RefreshQuery;
import dev.partenon.user.domain.model.response.KeysApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * EndPoint para refrescar los Tokens
 */
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
     * @param authorization Contiene el valor del Header de Authorization
     */
    @GetMapping("/refresh")
    public ResponseEntity<KeysApiModel> refreshToken(@RequestHeader(name = AUTHORIZATION) String authorization) throws Exception {
        var query = RefreshQuery.builder()
                .bearerToken(authorization)
                .build();

        var tokens = (KeysApiModel) queryBus.ask(query);

        return ResponseEntity.ok(tokens);
    }


}
