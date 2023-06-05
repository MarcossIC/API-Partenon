package dev.partenon.museumcontext.tours.application;

import dev.partenon.global.domain.abstractcomponents.command.CommandHandler;
import dev.partenon.museumcontext.core.infrastructure.persistence.MuseumRepository;
import dev.partenon.museumcontext.tours.domain.SaveTourCommand;
import dev.partenon.museumcontext.tours.domain.entity.MuseumTour;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static dev.partenon.global.infrastructure.UtilReactor.catchingQueryTimeoutException;

/**
 * Maneja el comando echo por SaveTourResource
 */
@AllArgsConstructor
@Service
public class SaveTourCommandHandler implements CommandHandler<SaveTourCommand> {
    @Autowired
    private TourRepository repository;
    @Autowired
    private MuseumRepository museumRepository;

    @Override
    public void handle(SaveTourCommand command) {
        Mono.just(command)
                .doOnNext(commandValues -> museumRepository.findByMuseumId(commandValues.getMuseumId())
                        .subscribeOn(Schedulers.immediate())
                        .switchIfEmpty(Mono.error(new RuntimeException("ID No registrado")))
                        .map(museum -> new MuseumTour(commandValues.getMuseumId(), commandValues.getTourName()))
                        .flatMap(repository::save)
                        .retryWhen(catchingQueryTimeoutException).log()
                ).checkpoint();
    }
}
