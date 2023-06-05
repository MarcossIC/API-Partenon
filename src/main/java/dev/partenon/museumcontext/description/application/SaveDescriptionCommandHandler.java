package dev.partenon.museumcontext.description.application;

import dev.partenon.global.domain.abstractcomponents.command.CommandHandler;
import dev.partenon.museumcontext.core.infrastructure.persistence.MuseumRepository;
import dev.partenon.museumcontext.description.doamin.MuseumDescription;
import dev.partenon.museumcontext.description.doamin.SaveDescriptionCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static dev.partenon.global.infrastructure.UtilReactor.catchingQueryTimeoutException;

@Service
@AllArgsConstructor
/**Maneja el comando de SaveDescriptionResource*/
public class SaveDescriptionCommandHandler implements CommandHandler<SaveDescriptionCommand> {
    @Autowired
    private DescriptionRepository descriptionRepository;
    @Autowired
    private MuseumRepository museumRepository;

    @Override
    public void handle(SaveDescriptionCommand command) {
        Mono.just(command)
                .doOnNext(commandValues ->
                        museumRepository.findByMuseumId(commandValues.getMuseumId())
                                .subscribeOn(Schedulers.immediate())
                                .switchIfEmpty(Mono.error(new RuntimeException("ID no registrado")))
                                .flatMap(museum -> {
                                    if (museum.getMuseumDescription() == null) {
                                        descriptionRepository.save(MuseumDescription.create(commandValues, museum))
                                                .retryWhen(catchingQueryTimeoutException);
                                    } else {
                                        commandValues.setFlag(false);
                                        museum.getMuseumDescription().setDescription(commandValues.getDescription());
                                    }
                                    return Mono.never();
                                })
                )
                .checkpoint();
    }
}
