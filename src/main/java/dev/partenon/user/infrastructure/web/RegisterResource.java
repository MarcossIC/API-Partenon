package dev.partenon.user.infrastructure.web;

import dev.partenon.global.domain.ports.command.CommandBus;
import dev.partenon.user.domain.models.UserQueryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
/**Endpoint para registrar museo y un usuario */
public class RegisterResource {
    private final CommandBus commandBus;

    /**
     * Registra un museo y al usuario. Tiene la funcion de sign-up
     *
     * @param userQueryCommand Modelo rest para el usuario y el museo
     * @return Void
     * @throws Exception
     */
    @PostMapping("/register")
    public HttpEntity<Void> saveUser(@RequestBody @Valid UserQueryCommand userQueryCommand) throws Exception {
        commandBus.dispatch(userQueryCommand);
        return ResponseEntity.created(new URI("http://localhost:8080/api/users")).build();
    }
}
