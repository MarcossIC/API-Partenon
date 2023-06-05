package dev.partenon.museumcontext.description.application;

import dev.partenon.museumcontext.description.doamin.MuseumDescription;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface DescriptionRepository extends ReactiveSortingRepository<MuseumDescription, Long> {
}
