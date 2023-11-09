package dev.partenon.museums.contact.infrastructure.web;

import dev.partenon.global.domain.ports.command.CommandBus;
import dev.partenon.museums.contact.domain.actions.SaveContactCommand;
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


@RestController
@RequestMapping("/api/v1/museums")
@RequiredArgsConstructor
public class SaveContactResource {
    private final CommandBus commandBus;

    /**
     * @param command   Commando a ejecutar
     * @param principal Objeto User con los datos post autenticarse
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/contacts")
    public HttpEntity<Void> saveContact(@RequestBody @Valid SaveContactCommand command, @AuthenticationPrincipal User principal) {
        command.setUserId(principal.id());
        commandBus.dispatch(command);

        return ResponseEntity.ok().build();
    }
}
