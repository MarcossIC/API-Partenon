package dev.partenon.museumcontext.contact.application;

import dev.partenon.global.domain.abstractcomponents.command.CommandHandler;
import dev.partenon.museumcontext.contact.doamin.SaveContactCommand;
import dev.partenon.museumcontext.contact.doamin.entity.MuseumContact;
import dev.partenon.museumcontext.core.application.MuseumRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static dev.partenon.global.infrastructure.UtilReactor.catchingQueryTimeoutException;


@Service
@AllArgsConstructor
/**Maneja el comando de SaveContactResource*/
public class SaveContactCommandHandler implements CommandHandler<SaveContactCommand> {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private MuseumRepository museumRepository;

    @Override
    public void handle(SaveContactCommand command) {
        Mono.just(command.getMuseumId())
                .flatMap(museumRepository::findByMuseumId)
                .subscribeOn(Schedulers.immediate())
                .switchIfEmpty(Mono.error(new RuntimeException("ID No registrado")))
                .map(museum -> MuseumContact.create(command))
                .doOnNext(contact -> {
                    if (!contact.getMuseum().getMuseumContacts().isEmpty()) {
                        contact.getMuseum().getMuseumContacts().forEach(x -> {
                            if (x.getContactPK().getType() == contact.getContactPK().getType())
                                command.setFlag(false);
                        });
                    }
                })
                .flatMap(contactRepository::save)
                .retryWhen(catchingQueryTimeoutException).log()
                .checkpoint();

    }
}
