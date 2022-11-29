package dev.partenon.museumcontext.openinghours.application;

import dev.partenon.global.domain.abstractcomponents.command.CommandHandler;
import dev.partenon.museumcontext.core.application.MuseumRepository;
import dev.partenon.museumcontext.openinghours.doamin.SaveOpeningHoursCommand;
import dev.partenon.museumcontext.openinghours.doamin.OpeningHours;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static dev.partenon.global.infrastructure.UtilReactor.catchingQueryTimeoutException;


@Service
@AllArgsConstructor
/**Maneja el comando para el SaveOpeningHourResource*/
public class SaveOpeningHoursCommandHandler implements CommandHandler<SaveOpeningHoursCommand> {
    @Autowired
    private MuseumRepository museumRepository;
    @Autowired
    private OpeningHoursRepository openingHoursRepository;

    @Override
    public void handle(SaveOpeningHoursCommand command) {
        Mono.just(command)
                .doOnNext(commandValues -> museumRepository.findByMuseumId(commandValues.getMuseumId())
                        .subscribeOn(Schedulers.immediate())
                        .switchIfEmpty(Mono.error(new RuntimeException("ID No registrado")))
                        .map(museum-> OpeningHours.create(commandValues, museum))
                        .flatMap(openingHoursRepository::save)
                        .retryWhen(catchingQueryTimeoutException).log()
                ).checkpoint();
    }
}
