package dev.partenon.museumcontext.contact.application;

import dev.partenon.museumcontext.contact.doamin.entity.MuseumContact;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface ContactRepository extends ReactiveSortingRepository<MuseumContact, Long> {
}
