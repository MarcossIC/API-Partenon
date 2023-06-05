package dev.partenon.museumcontext.appointment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.museumcontext.core.doamin.Museum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/**Entidad cita*/
public class Appointment implements Serializable {
    public static final Long serialVersionUID = 1L;

    private Long appointmentId;
    private LocalDate appointmentDate;
    private String language;
    private String requestedName;
    private String email;
    private String appointmentCode;

    @JsonIgnore
    private Museum museum;

    public static Appointment create(SaveAppointmentCommand command, Museum museum, String code) {
        var appointment = new Appointment();

        appointment.setRequestedName(command.getRequestedName());
        appointment.setEmail(command.getEmail());
        appointment.setLanguage(command.getLanguage());
        appointment.setAppointmentDate(LocalDate.parse(command.getAppointmentDate()));
        appointment.setAppointmentCode(code);
        appointment.setMuseum(museum);

        return appointment;
    }

}
