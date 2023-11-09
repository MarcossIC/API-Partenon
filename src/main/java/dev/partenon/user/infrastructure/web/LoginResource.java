package dev.partenon.user.infrastructure.web;

import dev.partenon.global.domain.ports.query.QueryBus;
import dev.partenon.user.domain.models.UserQueryCommand;
import dev.partenon.user.domain.models.response.KeysApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Endpoint para hacer Log-in
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginResource {
    private QueryBus queryBus;

    /**
     * Recurso para iniciar sesion dentro del sistema
     *
     * @param query Contiene los datos necesarios para el inicio de sesion
     * @return Devuelve un token JWT de inicio de sesion
     * @throws Exception
     */
    @PostMapping("/login")
    public HttpEntity<KeysApiModel> login(@RequestBody @Valid UserQueryCommand query) throws Exception {
        var response = (KeysApiModel) queryBus.ask(query);
        return ResponseEntity.ok(response);
    }
}
