package dev.partenon.museumcontext.tours.infrastructure;

import dev.partenon.global.domain.abstractcomponents.command.CommandBus;
import dev.partenon.museumcontext.tours.domain.SaveTourCommand;
import dev.partenon.museumcontext.tours.domain.TourRestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

/**EndPoint para manejar el guardado de Tours*/
@RestController
@RequestMapping("/api/museums")
public class SaveTourResource {
    private final CommandBus commandBus;

    @Autowired
    public SaveTourResource(CommandBus commandBus){
        this.commandBus = commandBus;
    }

    /**
     *
     * @param tourRestModel Modelo Rest para crear los Tours
     * @param museumId ID del Museo
     * @return {@link Void}
     * @throws Exception
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/tours")
    public Mono<ServerResponse> saveTour(
            @RequestBody @Valid TourRestModel tourRestModel,
            @RequestParam("key") String museumId) throws Exception{
        var command = SaveTourCommand.builder()
                        .tourName(tourRestModel.getTourName())
                        .museumId(Long.valueOf(museumId))
                        .build();

        commandBus.dispatch(command);


        return ServerResponse.created(
                        URI.create("http://localhost:8080/api/museums/tours&key=".concat(museumId)) )
                .build();
    }
}
