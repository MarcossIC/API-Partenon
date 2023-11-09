package dev.partenon.museums.contact.application;

import dev.partenon.global.domain.exceptions.ContentNotFoundException;
import dev.partenon.global.domain.ports.command.CommandHandler;
import dev.partenon.museums.contact.domain.actions.SaveContactCommand;
import dev.partenon.museums.contact.domain.models.ContactModel;
import dev.partenon.museums.contact.domain.ports.ContactRepository;
import dev.partenon.museums.core.domain.ports.MuseumRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class SaveContactCommandHandler implements CommandHandler<SaveContactCommand> {
    private ContactRepository repository;
    private MuseumRepository museumRepository;

    @Override
    public void handle(SaveContactCommand command) {
        if (!this.museumRepository.museumExistsTo(command.getMuseumId(), command.getUserId()))
            throw new ContentNotFoundException("Tu identificacion no tiene permitido o no pertenece al museo.", "SaveContactCommandHandler");

        var model = this.repository.findContact(command.getMuseumId(), command.getType());

        if (!command.getContact().equals(model.contact()))
            this.repository.saveContact(new ContactModel(command.getMuseumId(), command.getType(), command.getContact()));
    }
}
