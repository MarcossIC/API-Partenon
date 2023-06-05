package dev.partenon.museumcontext.description.doamin;

import dev.partenon.global.domain.abstractcomponents.command.Command;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveDescriptionCommand implements Command {
    private String description;
    private Long museumId;
    private Boolean flag;
}
