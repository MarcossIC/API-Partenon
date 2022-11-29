package dev.partenon.museumcontext.tours.application;

import dev.partenon.museumcontext.tours.domain.entity.MuseumTour;
import dev.partenon.museumcontext.tours.domain.entity.TourPK;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface TourRepository extends ReactiveSortingRepository<MuseumTour, TourPK> {
    //Recupera una lista de Tours por el ID de un museo
    Flux<MuseumTour> findByTourPKMuseumId(Long museumId);
}
