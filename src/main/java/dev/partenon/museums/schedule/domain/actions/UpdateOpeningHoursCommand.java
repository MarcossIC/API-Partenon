package dev.partenon.museums.schedule.domain.actions;

import dev.partenon.global.domain.ports.command.Command;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOpeningHoursCommand implements Command {
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;

    private Long museumId;
}
