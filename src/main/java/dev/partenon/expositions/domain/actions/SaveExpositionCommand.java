package dev.partenon.expositions.domain.actions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.global.domain.ports.command.Command;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class SaveExpositionCommand implements Command {
    @NotEmpty
    private final String museumId;
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String photo;
    @NotEmpty
    private final String description;
    @NotNull
    private final LocalDate endDate;

    @JsonIgnore
    private String userId;
}
