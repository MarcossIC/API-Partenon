package dev.partenon.museums.plan.infrastructure;

import dev.partenon.global.domain.ports.command.CommandBus;
import dev.partenon.museums.plan.domain.actions.SaveBuildingPlanCommand;
import dev.partenon.user.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Endpoint para guardar el plano del museo
 */
@RestController
@RequestMapping("/api/v1/museums")
@RequiredArgsConstructor
public class SaveBuildingPlanResource {
    private final CommandBus commandBus;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/plans")
    public HttpEntity<Void> saveBuildingPlan(
            @RequestBody @Valid SaveBuildingPlanCommand command, @AuthenticationPrincipal User principal) {
        command.setUserId(principal.id());
        commandBus.dispatch(command);

        return ResponseEntity.ok().build();
    }
}
