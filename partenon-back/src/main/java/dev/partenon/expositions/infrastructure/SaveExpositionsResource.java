package dev.partenon.expositions.infrastructure;

import dev.partenon.expositions.domain.SaveExpositionCommand;
import dev.partenon.expositions.domain.model.ExpositionRestModel;
import dev.partenon.global.domain.abstractcomponents.command.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.ResponseEntity.created;

/**Endpoint para guardar una expocicion*/
@RestController
@RequestMapping("/api/expositions")
public class SaveExpositionsResource {
    private final CommandBus commandBus;

    @Autowired
    public SaveExpositionsResource(CommandBus commandBus){
        this.commandBus = commandBus;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public Mono<ServerResponse> saveExposition(@RequestBody @Valid ExpositionRestModel expositionRestModel,
                                               @RequestParam("key") String museumId) throws Exception {

        var command = SaveExpositionCommand.builder()
                .endDate(expositionRestModel.getEndDate())
                .description(expositionRestModel.getDescription())
                .name(expositionRestModel.getName())
                .category(expositionRestModel.getCategory())
                .museumId(Long.parseLong(museumId))
                .build();

        commandBus.dispatch(command);


        return ServerResponse.created(
                        URI.create("http://localhost:8080/api/expositions&key=".concat(museumId) ) )
                        .build();
    }
}
