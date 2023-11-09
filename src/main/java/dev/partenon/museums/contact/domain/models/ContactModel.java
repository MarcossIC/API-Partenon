package dev.partenon.museums.contact.domain.models;

public record ContactModel(String museumId, SocialMediaType type, String contact) {
    public ContactModel(String contact) {
        this(null, null, contact);
    }
}
