package dev.partenon.museumcontext.appointment.application;

import dev.partenon.global.domain.abstractcomponents.command.CommandHandler;
import dev.partenon.global.domain.abstractcomponents.event.EventBus;
import dev.partenon.museumcontext.appointment.domain.Appointment;
import dev.partenon.museumcontext.appointment.domain.SaveAppointmentCommand;
import dev.partenon.museumcontext.appointment.domain.SendEmailEvent;
import dev.partenon.museumcontext.core.application.MuseumRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicMarkableReference;

import static dev.partenon.global.infrastructure.UtilReactor.catchingQueryTimeoutException;

@AllArgsConstructor
@Service
/**Maneja el comando de SaveAppointmentResource*/
public class SaveAppointmentCommandHandler implements CommandHandler<SaveAppointmentCommand> {
    @Autowired
    private AppointmentRepository repository;
    @Autowired
    private MuseumRepository museumRepository;
    @Autowired
    private EventBus eventBus;

    @Override
    public void handle(SaveAppointmentCommand command) {
        Mono.defer(() -> Mono.just(command.getMuseumId())
                .flatMap(museumRepository::findByMuseumId)
                .switchIfEmpty(Mono.error(new Exception("ID no registrado")))
                .subscribeOn(Schedulers.boundedElastic())
                .map(museum -> {
                    var code = new AtomicMarkableReference<String>(UUID.randomUUID().toString().split("-")[0], true);
                    while (repository.existsByAppointmentCode(code.getReference())) {
                        code.compareAndSet(code.getReference(), UUID.randomUUID().toString().split("-")[0], true, true);
                    }
                    var event = SendEmailEvent.builder()
                            .email(command.getEmail())
                            .appointmentDate(command.getAppointmentDate())
                            .code(code.getReference())
                            .subject("Reservacion de Turno ".concat(code.getReference()))
                            .requestedName(command.getRequestedName())
                            .museum(museum)
                            .build();
                    return event;
                })
                .flatMap(e -> {
                    Boolean response = null;
                    try {
                        response = eventBus.handle(e);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    if (!response) throw new RuntimeException("El email no es valido");
                    return Mono.just(e);
                })
                .onErrorComplete()
                .doOnNext(event ->
                        repository.save(Appointment.create(command, event.getMuseum(), event.getCode()))
                                .retryWhen(catchingQueryTimeoutException)));
    }
}
