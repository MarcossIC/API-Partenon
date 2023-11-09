package dev.partenon.expositions.application;

import dev.partenon.expositions.domain.actions.SaveExpositionCommand;
import dev.partenon.expositions.domain.model.ExpositionModel;
import dev.partenon.expositions.domain.ports.ExpositionRepository;
import dev.partenon.global.domain.exceptions.ContentNotFoundException;
import dev.partenon.global.domain.ports.command.CommandHandler;
import dev.partenon.museums.core.domain.ports.MuseumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveExpositionCommandHandler implements CommandHandler<SaveExpositionCommand> {
    private final ExpositionRepository repository;
    private final MuseumRepository museumRepository;

    //
    @Override
    public void handle(SaveExpositionCommand command) {
        if (command.getUserId() == null) throw new IllegalArgumentException("User ID canoot be null");

        if (!this.museumRepository.museumExistsTo(command.getMuseumId(), command.getUserId()))
            throw new ContentNotFoundException("Tu identificacion no tiene permitido o no pertenece al museo.", "SaveContactCommandHandler");

        this.repository.saveExposition(new ExpositionModel(command.getMuseumId(), command.getName(), command.getPhoto(), command.getDescription(), command.getEndDate()));
    }
}
