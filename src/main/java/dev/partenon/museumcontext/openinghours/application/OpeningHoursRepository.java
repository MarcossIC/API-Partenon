package dev.partenon.museumcontext.openinghours.application;

import dev.partenon.museumcontext.core.doamin.Museum;
import dev.partenon.museumcontext.openinghours.doamin.OpeningHours;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;


public interface OpeningHoursRepository extends ReactiveSortingRepository<OpeningHours, Long> {

    /**
     * Busca un horario de apertura con un museo
     */
    Mono<OpeningHours> findByMuseum(Museum museum);

    /**
     * Comprueba que el horario de apertura xista
     */
    Boolean existsByOpeningHoursId(Long id);
}
