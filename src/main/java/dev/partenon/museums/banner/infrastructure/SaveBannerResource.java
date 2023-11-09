package dev.partenon.museums.banner.infrastructure;

import dev.partenon.global.domain.ports.command.CommandBus;
import dev.partenon.museums.banner.domain.actions.SaveBannerCommand;
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
public class SaveBannerResource {
    private final CommandBus commandBus;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/banners")
    public HttpEntity<Void> saveBanners(@RequestBody @Valid SaveBannerCommand command, @AuthenticationPrincipal User principal) throws Exception {
        command.setUserId(principal.id());
        commandBus.dispatch(command);

        return ResponseEntity.ok().build();
    }
}
