package dev.partenon.museums.core.infrastructure.web;

import dev.partenon.global.domain.model.MuseumIdModel;
import dev.partenon.global.domain.ports.event.EventBus;
import dev.partenon.museums.core.domain.actions.MuseumSaveEvent;
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
import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SaveMuseumResource {
    private final EventBus eventBus;

    /**
     * Registra un museo y al usuario. Tiene la funcion de sign-up
     *
     * @param event Evento con los datos del museo
     * @return Void
     * @throws Exception
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/museums")
    public HttpEntity<MuseumIdModel> saveMuseum(@RequestBody @Valid MuseumSaveEvent event, @AuthenticationPrincipal User principal) throws Exception {
        if (!event.getUserId().equals(principal.id()))
            throw new IllegalArgumentException("Tu identificacion no puede crear museos para el ID que ingresaste");

        var museumId = eventBus.execute(event);
        return ResponseEntity.created(new URI("http://localhost:8080/api/users")).body(museumId);
    }

}
