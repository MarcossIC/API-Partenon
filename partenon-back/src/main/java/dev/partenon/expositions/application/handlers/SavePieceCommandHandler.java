package dev.partenon.expositions.application.handlers;

import dev.partenon.expositions.application.ExpositionRepository;
import dev.partenon.expositions.application.PieceRepository;
import dev.partenon.expositions.domain.Pieces;
import dev.partenon.expositions.domain.SavePieceCommand;
import dev.partenon.global.domain.abstractcomponents.command.CommandHandler;
import dev.partenon.global.infrastructure.UtilReactor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

import static dev.partenon.global.infrastructure.UtilReactor.catchingQueryTimeoutException;


@Service
@AllArgsConstructor
/**Maneja el comando del SavePieceResource*/
public class SavePieceCommandHandler implements CommandHandler<SavePieceCommand> {
    @Autowired
    private ExpositionRepository expositionRepository;
    @Autowired
    private PieceRepository repository;

    @Override
    public void handle(SavePieceCommand command)  {
        Mono.just(command.getExpositionId())
                .map(expositionRepository::findByExpositionId)
                    .subscribeOn(Schedulers.parallel())
                    .switchIfEmpty(Mono.error(new Exception("Este ID no esta registrado")))
                    .delayElement(Duration.ofMillis(2))
                .flatMap(event-> event
                        .doOnNext(exposition->
                                repository.save(Pieces.create(command, exposition))
                                        .retryWhen(catchingQueryTimeoutException)));

    }
}
