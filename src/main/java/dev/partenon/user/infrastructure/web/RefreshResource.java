package dev.partenon.user.infrastructure.web;

import dev.partenon.global.domain.ports.query.QueryBus;
import dev.partenon.user.domain.models.User;
import dev.partenon.user.domain.models.querys.RefreshQuery;
import dev.partenon.user.domain.models.response.KeysApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * EndPoint para refrescar los Tokens
 */
@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class RefreshResource {
    private final QueryBus queryBus;


    /**
     * Se llama cuando el {@code Access-Token} ha expirado
     * Crea un nuevo {@code Access-Token y Refresh-Token} con el {@code Refresh-Token} anterior
     *
     * @param principal Contiene los datos del User que se autenticaron en el filtro
     */
    @GetMapping("/refresh")
    public HttpEntity<KeysApiModel> refreshToken(@AuthenticationPrincipal User principal) throws Exception {
        var query = RefreshQuery.builder()
                .user(principal)
                .build();

        var tokens = (KeysApiModel) queryBus.ask(query);

        return ResponseEntity.ok(tokens);
    }


}
