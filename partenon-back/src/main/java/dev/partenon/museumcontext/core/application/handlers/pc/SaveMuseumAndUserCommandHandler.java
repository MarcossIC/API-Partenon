package dev.partenon.museumcontext.core.application.handlers.pc;

import dev.partenon.global.domain.abstractcomponents.command.CommandHandler;
import dev.partenon.museumcontext.core.application.MuseumRepository;
import dev.partenon.museumcontext.core.doamin.Museum;
import dev.partenon.museumcontext.core.doamin.SaveMuseumAndUserCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static dev.partenon.global.infrastructure.UtilReactor.catchingQueryTimeoutException;


@Slf4j
@Service
@Transactional
/**Maneja el comando de SaveMuseumAndUserResource*/
public class SaveMuseumAndUserCommandHandler implements CommandHandler<SaveMuseumAndUserCommand> {
    @Autowired
    private MuseumRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void handle(SaveMuseumAndUserCommand command) {
        Mono.just(command)
                .doOnNext(museum -> {
                    var museumGhost =repository.findByUserUsernameOrUserEmail(museum.getUsername(), museum.getUsername()).subscribeOn(Schedulers.immediate());
                    if (Boolean.TRUE.equals(museumGhost.hasElement().block())) throw new RuntimeException("Username ya se encuentra en uso");
                })
                .doOnNext(museum-> {
                    var museumGhost =repository.findByUserUsernameOrUserEmail(museum.getEmail(), museum.getEmail()).subscribeOn(Schedulers.immediate());
                    if (Boolean.TRUE.equals(museumGhost.hasElement().block())) throw new RuntimeException("Email ya se encuentra en uso");
                })
                .map(museum-> Museum.create(museum, passwordEncoder))
                .flatMap(repository::save)
                .retryWhen(catchingQueryTimeoutException).log()
                .checkpoint();
    }
}
