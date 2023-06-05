package dev.partenon.museumcontext.plan.infrastructure;

import dev.partenon.global.domain.abstractcomponents.command.CommandBus;
import dev.partenon.museumcontext.plan.doamin.BuildingPlanRestModel;
import dev.partenon.museumcontext.plan.doamin.SaveBuildingPlanCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

/**
 * Endpoint para guardar el plano del museo
 */
@RestController
@RequestMapping("/api/museums")
public class SaveBuildingPlanResource {
    private final CommandBus commandBus;

    @Autowired
    public SaveBuildingPlanResource(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/plans")
    public Mono<ServerResponse> saveBuildingPlan(@RequestBody @Valid BuildingPlanRestModel buildingPlan,
                                                 @RequestParam("key") String museumId) throws Exception {
        var command = SaveBuildingPlanCommand.builder()
                .buildingPlan(buildingPlan.getBuildingPlan())
                .museumId(Long.parseLong(museumId))
                .build();

        commandBus.dispatch(command);

        return ServerResponse.created(
                        new URI("http://localhost:8080/api/museums/plans&key=".concat(museumId)))
                .build();
    }
}
