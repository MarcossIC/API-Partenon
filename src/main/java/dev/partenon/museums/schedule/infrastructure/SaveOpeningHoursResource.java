package dev.partenon.museums.schedule.infrastructure;

import dev.partenon.global.domain.ports.command.CommandBus;
import dev.partenon.museums.schedule.domain.actions.SaveOpeningHoursCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Endpoint para guardar horarios de apertura
 */
@RestController
@RequestMapping("/api/v1/museums")
@RequiredArgsConstructor
public class SaveOpeningHoursResource {
    private final CommandBus commandBus;

    // d<<
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/schedules")
    public HttpEntity<Void> saveOpeningHour(@RequestBody @Valid SaveOpeningHoursCommand command) throws Exception {

        commandBus.dispatch(command);

        return ResponseEntity.ok().build();
    }
}
