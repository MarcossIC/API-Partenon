package dev.partenon.museumcontext.appointment.domain;

import dev.partenon.global.domain.abstractcomponents.command.Command;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveAppointmentCommand implements Command {
    private String requestedName;
    private String email;
    private String language;
    private String appointmentDate;
    private Long museumId;
}
