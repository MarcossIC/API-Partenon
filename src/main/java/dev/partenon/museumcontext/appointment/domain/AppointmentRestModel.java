package dev.partenon.museumcontext.appointment.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class AppointmentRestModel {
    @NotEmpty
    private String requestedName;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String language;
    @NotEmpty
    private String appointmentDate;
}
