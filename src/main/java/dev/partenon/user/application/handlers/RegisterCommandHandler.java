package dev.partenon.user.application.handlers;

import dev.partenon.global.domain.EntityNotFoundException;
import dev.partenon.global.domain.abstractcomponents.command.CommandHandler;
import dev.partenon.museumcontext.core.doamin.Museum;
import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.user.domain.RegisterCommand;
import dev.partenon.user.domain.ports.UserMapperPort;
import dev.partenon.user.domain.ports.UserServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
/**Maneja el comando de SaveMuseumAndUserResource*/
public class RegisterCommandHandler implements CommandHandler<RegisterCommand> {
    private final UserMapperPort mapper;
    private final UserServicePort service;


    /**
     * Ejecuta el comando de registrar al usuario
     *
     * @param command Comando con los datos parael registro
     */
    @Override
    public void handle(RegisterCommand command) {
        var ghost = service.retrieveUserByUsername(command.getUsername());
        if(ghost != null){
            throw new RuntimeException("Username in use");
        }
        ghost = service.retrieveUserByEmail(command.getEmail());
        if(ghost != null){
            throw new RuntimeException("Email in use");
        }
        var user = mapper.mapUser(command);
        service.save(user);

    }
}
