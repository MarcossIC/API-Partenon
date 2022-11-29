package dev.partenon.museumcontext.tours.domain;

import dev.partenon.global.domain.abstractcomponents.command.Command;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveTourCommand implements Command {
    private String tourName;
    private Long museumId;
}
