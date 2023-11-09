package dev.partenon.museums.schedule.application;

import dev.partenon.global.domain.exceptions.ContentNotFoundException;
import dev.partenon.global.domain.ports.command.CommandHandler;
import dev.partenon.museums.core.domain.ports.MuseumRepository;
import dev.partenon.museums.schedule.domain.actions.SaveOpeningHoursCommand;
import dev.partenon.museums.schedule.domain.models.ScheduleModel;
import dev.partenon.museums.schedule.domain.ports.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SaveOpeningHoursCommandHandler implements CommandHandler<SaveOpeningHoursCommand> {
    private final ScheduleRepository repository;
    private final MuseumRepository museumRepository;

    @Override
    public void handle(SaveOpeningHoursCommand command) {
        if (!this.museumRepository.museumExistsTo(command.getMuseumId(), command.getUserId()))
            throw new ContentNotFoundException("Tu identificacion no tiene permitido o no pertenece al museo.", "SaveContactCommandHandler");

        this.repository.saveContact(new ScheduleModel(command.getMuseumId(), command.getDay(), command.getShift(), command.getOpening(), command.getClose()));
    }
}
