package dev.partenon.user.application;

import dev.partenon.global.domain.exceptions.ContentInUseException;
import dev.partenon.global.domain.ports.command.CommandHandler;
import dev.partenon.user.domain.models.User;
import dev.partenon.user.domain.models.UserQueryCommand;
import dev.partenon.user.domain.ports.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public final class RegisterCommandHandler implements CommandHandler<UserQueryCommand> {
    private final UserRepository repository;

    /**
     * Ejecuta el comando de registrar al usuario
     *
     * @param command Comando con los datos parael registro
     */
    @Override
    public void handle(UserQueryCommand command) {
        if (repository.isRegisteredUser(command.getEmail()))
            throw new ContentInUseException("Email ya se encuentra en uso", "RegisterCommandHandler");

        var user = new User(UUID.randomUUID().toString(), command.getEmail(), command.getPassword());
        repository.save(user);
    }
}
