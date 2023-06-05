package dev.partenon.museumcontext.openinghours.application;

import dev.partenon.global.domain.abstractcomponents.command.CommandHandler;
import dev.partenon.museumcontext.core.doamin.Museum;
import dev.partenon.museumcontext.openinghours.doamin.OpeningHours;
import dev.partenon.museumcontext.openinghours.doamin.UpdateOpeningHoursCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
/**Maneja el comando de UpdateOpeningHourResource*/
public class UpdateOpeningHoursCommandHandler implements CommandHandler<UpdateOpeningHoursCommand> {
    @Autowired
    private OpeningHoursRepository repository;

    @Override
    public void handle(UpdateOpeningHoursCommand command) {
        Mono.just(command)
                .doOnNext(commandValues -> repository.findByMuseum(new Museum(commandValues.getMuseumId()))
                        .subscribeOn(Schedulers.immediate())
                        .switchIfEmpty(Mono.error(new RuntimeException("ID No registrado")))
                        .map(currentHours -> OpeningHours.create(commandValues, currentHours))
                        .flatMap(repository::save).log()
                ).checkpoint();
    }
}
