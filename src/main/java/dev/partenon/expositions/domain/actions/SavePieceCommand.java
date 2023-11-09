package dev.partenon.expositions.domain.actions;

import dev.partenon.global.domain.ports.command.Command;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SavePieceCommand implements Command {
    private String pieceName;
    private String description;
    private String photo;
    private Long expositionId;
}
