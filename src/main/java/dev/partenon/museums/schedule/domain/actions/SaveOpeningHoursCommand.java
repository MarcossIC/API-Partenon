package dev.partenon.museums.schedule.domain.actions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.global.domain.ports.command.Command;
import dev.partenon.museums.schedule.domain.models.Days;
import dev.partenon.museums.schedule.domain.models.Shifts;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public final class SaveOpeningHoursCommand implements Command {
    @NotEmpty
    private final String museumId;
    @NotNull
    private final Days day;
    @NotNull
    private final Shifts shift;
    @NotEmpty
    private final String opening;
    @NotEmpty
    private final String close;
    @JsonIgnore
    private String userId;
}
