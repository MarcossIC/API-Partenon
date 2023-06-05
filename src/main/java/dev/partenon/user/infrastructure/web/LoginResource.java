package dev.partenon.user.infrastructure.web;

import dev.partenon.global.domain.abstractcomponents.query.QueryBus;
import dev.partenon.user.domain.LoginQuery;
import dev.partenon.user.domain.model.response.KeysApiModel;
import dev.partenon.user.domain.model.rest.LoginUserRestModel;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/auth")
public class LoginResource {
    @Autowired
    private QueryBus queryBus;

    /**
     * Recurso para iniciar sesion dentro del sistema
     *
     * @param loginUser Contiene los datos necesarios para el inicio de sesion
     * @return Devuelve un token JWT de inicio de sesion
     * @throws Exception
     */
    @PostMapping("/login")
    public ResponseEntity<KeysApiModel> login(@RequestBody @Valid LoginUserRestModel loginUser) throws Exception {
        var query = LoginQuery.builder()
                .usernameOrEmail(loginUser.getUsernameOrEmail())
                .password(loginUser.getPassword())
                .build();

        var response = (KeysApiModel) queryBus.ask(query);
        return ResponseEntity.ok(response);
    }
}
