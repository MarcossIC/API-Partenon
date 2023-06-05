package dev.partenon.museumcontext.openinghours.infrastructure;

import dev.partenon.global.domain.abstractcomponents.command.CommandBus;
import dev.partenon.museumcontext.openinghours.doamin.OpeningHoursRestModel;
import dev.partenon.museumcontext.openinghours.doamin.UpdateOpeningHoursCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * Endpoint para actualizar Horarios de apertura
 */
@RestController
@RequestMapping("/api/museums")
public class UpdateOpeningHoursResource {
    private final CommandBus commandBus;

    @Autowired
    public UpdateOpeningHoursResource(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    /**
     * @param openingHoursRestModel Model Rest con los nuevos datos
     * @param museumId              ID del museo que se actualizara
     * @return Void
     * @throws Exception
     */
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/openings")
    public Mono<ServerResponse> updateOpeningHours(@RequestBody @Valid OpeningHoursRestModel openingHoursRestModel,
                                                   @RequestParam("key") String museumId) throws Exception {
        var command = UpdateOpeningHoursCommand.builder()
                .monday(openingHoursRestModel.getMonday())
                .tuesday(openingHoursRestModel.getTuesday())
                .wednesday(openingHoursRestModel.getWednesday())
                .thursday(openingHoursRestModel.getThursday())
                .friday(openingHoursRestModel.getFriday())
                .saturday(openingHoursRestModel.getSaturday())
                .sunday(openingHoursRestModel.getSunday())
                .museumId(Long.parseLong(museumId))
                .build();

        commandBus.dispatch(command);

        return ServerResponse.noContent().build();
    }
}
