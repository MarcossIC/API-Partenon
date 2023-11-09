package dev.partenon.expositions.infrastructure.web;

import dev.partenon.expositions.domain.actions.SaveExpositionCommand;
import dev.partenon.expositions.domain.model.ExpositionModel;
import dev.partenon.global.domain.ports.command.CommandBus;
import dev.partenon.user.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/expositions")
@RequiredArgsConstructor
public class SaveExpositionsResource {
    private final CommandBus commandBus;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public HttpEntity<Void> saveExposition(@RequestBody @Valid SaveExpositionCommand command, @AuthenticationPrincipal User principal) {
        command.setUserId(principal.id());
        commandBus.dispatch(command);

        return ResponseEntity.created(URI.create("http://localhost:8080/api/expositions")).build();
    }
}
//