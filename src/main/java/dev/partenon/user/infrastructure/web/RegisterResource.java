package dev.partenon.user.infrastructure.web;

import dev.partenon.global.domain.abstractcomponents.command.CommandBus;
import dev.partenon.user.domain.RegisterCommand;
import dev.partenon.user.domain.model.rest.UserRestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
/**Endpoint para registrar museo y un usuario */
public class RegisterResource {
    @Autowired
    private CommandBus commandBus;

    /**
     * Registra un museo y al usuario. Tiene la funcion de sign-up
     *
     * @param userRestModel Modelo rest para el usuario y el museo
     * @return Void
     * @throws Exception
     */
    @PostMapping("/museums")
    public ResponseEntity saveMuseumAndUsername(@RequestBody @Valid UserRestModel userRestModel) throws Exception {

        var command = RegisterCommand.builder()
                .username(userRestModel.getUsername())
                .password(userRestModel.getPassword())
                .email(userRestModel.getEmail())
                .museumName(userRestModel.getMuseumName())
                .province(userRestModel.getProvince())
                .city(userRestModel.getCity())
                .street(userRestModel.getStreet())
                .addressNumber(userRestModel.getAddressNumber())
                .build();

        commandBus.dispatch(command);

        return ResponseEntity.created(new URI("http://localhost:8080/api/auth/museums")).build();
    }
}
