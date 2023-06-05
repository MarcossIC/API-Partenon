package dev.partenon.museumcontext.appointment.domain;

import dev.partenon.global.domain.abstractcomponents.event.Event;
import dev.partenon.museumcontext.core.doamin.Museum;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@Builder
public class SendEmailEvent extends Event<Boolean> {
    @Email
    private String email;
    private String subject;

    private Museum museum;
    private String requestedName;
    private String code;
    private String appointmentDate;
}
