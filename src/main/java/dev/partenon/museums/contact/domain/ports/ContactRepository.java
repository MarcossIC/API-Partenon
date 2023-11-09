package dev.partenon.museums.contact.domain.ports;

import dev.partenon.museums.contact.domain.models.ContactModel;
import dev.partenon.museums.contact.domain.models.SocialMediaType;

public interface ContactRepository {
    ContactModel saveContact(ContactModel model);

    ContactModel findContact(String museumId, SocialMediaType type);
}
