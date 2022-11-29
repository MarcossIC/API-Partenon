package dev.partenon.expositions.application;

import dev.partenon.expositions.domain.Expositions;
import dev.partenon.museumcontext.core.doamin.Museum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExpositionRepository extends ReactiveSortingRepository<Expositions, Long> {
    /**Paginar expociciones de un museo*/
    Flux<Expositions> findByMuseum(Museum museum, Pageable pageable);

    Mono<Expositions> findByExpositionId(Long expositionId);
}
