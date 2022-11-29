package dev.partenon.museumcontext.appointment.infrastructure;

import dev.partenon.global.domain.abstractcomponents.command.CommandBus;
import dev.partenon.museumcontext.appointment.domain.SaveAppointmentCommand;
import dev.partenon.museumcontext.appointment.domain.AppointmentRestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/museums")
/**Endpoint para registrar un cita al museo*/
public class SaveAppointmentResource {
    private final CommandBus commandBus;

    @Autowired
    public SaveAppointmentResource(CommandBus commandBus){
        this.commandBus = commandBus;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/appointments")
    public Mono<ServerResponse> saveAppointment(
            @RequestBody @Valid AppointmentRestModel appointmentRestModel,
            @RequestParam("key") String museumId) throws Exception {

        var command = SaveAppointmentCommand.builder()
                .requestedName(appointmentRestModel.getRequestedName())
                .email(appointmentRestModel.getEmail())
                .language(appointmentRestModel.getLanguage())
                .appointmentDate(appointmentRestModel.getAppointmentDate())
                .museumId(Long.valueOf(museumId))
                        .build();

        commandBus.dispatch(command);

        return ServerResponse.created(
                        new URI("http://localhost:8080/api/museums/appointments&key="
                                .concat(museumId)))
                .build();
    }
}
