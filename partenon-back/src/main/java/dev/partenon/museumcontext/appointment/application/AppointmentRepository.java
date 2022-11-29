package dev.partenon.museumcontext.appointment.application;

import dev.partenon.museumcontext.appointment.domain.Appointment;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface AppointmentRepository extends ReactiveSortingRepository<Appointment, Long> {
        Boolean existsByAppointmentCode(String code);
}
