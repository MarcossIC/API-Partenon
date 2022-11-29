package dev.partenon.global.infrastructure;

import dev.partenon.global.domain.abstractcomponents.MailPort;
import dev.partenon.global.domain.abstractcomponents.event.EventHandler;
import dev.partenon.museumcontext.appointment.domain.SendEmailEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class SendEmailEventHandler implements EventHandler<Boolean, SendEmailEvent> {
    @Autowired
    private MailPort service;

    @Override
    public Boolean handle(SendEmailEvent event) throws Exception {

        var message = new StringBuilder();

        message.append("Hola! ");
        message.append(event.getRequestedName());
        message.append("\n¡Su turno para el museo ");
        message.append(event.getMuseum().getMuseumName());
        message.append(" ya ha sido reservado! ");
        message.append("Para la fecha ");
        message.append(event.getAppointmentDate());
        message.append("\nDebera presentarse por ventanilla con su codigo de cita. ");
        message.append("Lo estaremos esperando");
        message.append("Codigo de Cita: ");
        message.append(event.getCode());

        log.info(message.toString());

        return service.send(event.getEmail(), event.getSubject(), message.toString());
    }
}
