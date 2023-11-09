package dev.partenon.museums.core.domain.actions;

import dev.partenon.global.domain.model.MuseumIdModel;
import dev.partenon.global.domain.ports.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@AllArgsConstructor
public final class MuseumSaveEvent extends Event<MuseumIdModel> {
    @NotEmpty
    private final String userId;
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String province;
    @NotEmpty
    private final String city;
    @NotEmpty
    private final String street;
    @NotEmpty
    private final String addressNumber;
    @NotEmpty
    private final String description;
}
