package dev.partenon.museumcontext.contact.doamin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.museumcontext.contact.doamin.SaveContactCommand;
import dev.partenon.museumcontext.contact.doamin.SocialMediaType;
import dev.partenon.museumcontext.core.doamin.Museum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
/**Entidad Contacto del museo*/
public class MuseumContact implements Serializable {
    public static final Long serialVersionUID = 1L;
    private ContactPK contactPK;
    private String museumContact;
    @JsonIgnore
    private Museum museum;

    public MuseumContact(String museumContact) {
        this.museumContact = museumContact;
    }

    public static MuseumContact create(SaveContactCommand command) {
        var contact = new MuseumContact(command.getContact());
        var type = SocialMediaType.EMAIL;
        if (command.getType().equals("WHATSAPP")) {
            type = SocialMediaType.WSP;
        } else {
            type = SocialMediaType.valueOf(command.getType());
        }
        contact.setContactPK(new ContactPK(command.getMuseumId(), type));

        return contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MuseumContact that = (MuseumContact) o;

        return new EqualsBuilder().append(contactPK, that.contactPK).append(museumContact, that.museumContact).append(museum, that.museum).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(contactPK).append(museumContact).append(museum).toHashCode();
    }

    @Override
    public String toString() {
        return "MuseumContact{" +
                "contactPK=" + contactPK +
                ", museumContact='" + museumContact + '\'' +
                '}';
    }
}

