package dev.partenon.museums.contact.infrastructure.persistence;

import dev.partenon.museums.contact.domain.models.ContactModel;
import dev.partenon.museums.contact.domain.models.SocialMediaType;
import dev.partenon.museums.contact.domain.ports.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactRepositoryAdapter implements ContactRepository {
    private final ContactJpaRepository jpaRepository;


    @Override
    public ContactModel saveContact(ContactModel model) {
        this.jpaRepository.save(ContactEntity.model.map(model));
        return model;
    }

    @Override
    public ContactModel findContact(String museumId, SocialMediaType type) {
        return this.jpaRepository.findById(new ContactPK(museumId, type))
                .map(entity -> entity.entity.map(null))
                .orElse(new ContactModel(""));
    }
}
