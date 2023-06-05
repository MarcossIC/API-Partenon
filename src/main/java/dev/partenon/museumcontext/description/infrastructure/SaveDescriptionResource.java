package dev.partenon.museumcontext.description.infrastructure;

import dev.partenon.global.domain.abstractcomponents.command.CommandBus;
import dev.partenon.museumcontext.description.doamin.DescriptionModel;
import dev.partenon.museumcontext.description.doamin.SaveDescriptionCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

/**
 * Endpoint para guardar descripcion
 */
@RestController
@RequestMapping("/api/museums")
public class SaveDescriptionResource {
    private final CommandBus commandBus;

    @Autowired
    public SaveDescriptionResource(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    /**
     * @param description Modelo para la descripcion
     * @param museumId    ID del museo
     * @return
     * @throws Exception
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/descriptions")
    public Mono<ServerResponse> saveDescription(@RequestBody @Valid DescriptionModel description,
                                                @RequestParam("key") String museumId) throws Exception {
        var command = SaveDescriptionCommand.builder()
                .description(description.getDescription())
                .museumId(Long.valueOf(museumId))
                .flag(true)
                .build();

        commandBus.dispatch(command);
        if (command.getFlag())
            return ServerResponse.created(new URI("http://localhost:8080/api/museums/descriptions&key=".concat(museumId))).build();

        return ServerResponse.noContent().build();
    }
}
