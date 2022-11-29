package dev.partenon.museumcontext.core.infrastructure.pc;

import dev.partenon.global.domain.abstractcomponents.command.CommandBus;
import dev.partenon.museumcontext.core.doamin.SaveMuseumAndUserCommand;
import dev.partenon.user.domain.model.UserRestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
/**Endpoint para registrar museo y un usuario */
public class SaveMuseumAndUserResource {
    @Autowired
    private CommandBus commandBus;

    /**
     * Registra un museo y al usuario. Tiene la funcion de sign-up
     * @param userRestModel Modelo rest para el usuario y el museo
     * @return Void
     * @throws Exception
     */
    @PostMapping("/museums")
    public Mono<ServerResponse> saveMuseumAndUsername(@RequestBody @Valid UserRestModel userRestModel) throws Exception {

        var command = SaveMuseumAndUserCommand.builder()
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
        return ServerResponse.created(new URI("http://localhost:8080/api/auth/museums")).build();
    }
}
