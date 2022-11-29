package dev.partenon.expositions.application.handlers;

import dev.partenon.expositions.application.ExpositionRepository;
import dev.partenon.expositions.domain.Expositions;
import dev.partenon.expositions.domain.SaveExpositionCommand;
import dev.partenon.global.domain.abstractcomponents.command.CommandHandler;
import dev.partenon.museumcontext.core.application.MuseumRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

import static dev.partenon.global.infrastructure.UtilReactor.catchingQueryTimeoutException;

@Service
@AllArgsConstructor
/**Maneja el comando del SaveExpositionResource*/
public class SaveExpositionCommandHandler implements CommandHandler<SaveExpositionCommand> {
    @Autowired
    private ExpositionRepository expositionRepository;
    @Autowired
    private MuseumRepository museumRepository;

    @Override
    public void handle(SaveExpositionCommand command) {
        Mono.just(command.getMuseumId())
                .map(museumRepository::findByMuseumId)
                    .subscribeOn(Schedulers.parallel())
                    .switchIfEmpty(Mono.error(new Exception("Este ID no esta registrado")))
                    .delayElement(Duration.ofMillis(2))
                .flatMap(event-> event
                        .doOnNext(museum->
                                expositionRepository.save(Expositions.create(command, museum))
                                        .retryWhen(catchingQueryTimeoutException)));

    }
}
