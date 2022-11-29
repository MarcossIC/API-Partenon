package dev.partenon.museumcontext.appointment.domain;

import dev.partenon.global.domain.abstractcomponents.event.Event;
import dev.partenon.museumcontext.core.doamin.Museum;
import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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
