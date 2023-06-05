package dev.partenon.museumcontext.openinghours.infrastructure;

import dev.partenon.global.domain.abstractcomponents.command.CommandBus;
import dev.partenon.museumcontext.openinghours.doamin.OpeningHoursRestModel;
import dev.partenon.museumcontext.openinghours.doamin.SaveOpeningHoursCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

/**
 * Endpoint para guardar horarios de apertura
 */
@RestController
@RequestMapping("/api/museums")
public class SaveOpeningHoursResource {
    private final CommandBus commandBus;

    @Autowired
    public SaveOpeningHoursResource(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/openings")
    public Mono<ServerResponse> saveOpeningHour(@RequestBody @Valid OpeningHoursRestModel openingHoursRestModel,
                                                @RequestParam("key") String museumId) throws Exception {
        var command = SaveOpeningHoursCommand.builder()
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

        return ServerResponse
                .created(new URI("http://localhost:8080/api/museums/openings&key=".concat(museumId)))
                .build();
    }
}
