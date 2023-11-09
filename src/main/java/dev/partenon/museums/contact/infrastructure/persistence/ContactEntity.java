package dev.partenon.museums.contact.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.global.domain.ports.Mapper;
import dev.partenon.museums.contact.domain.models.ContactModel;
import dev.partenon.museums.core.infrastructure.persistence.MuseumEntity;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "museum_contact")
/**Entidad Contacto del museo*/
public class ContactEntity implements Serializable {
    public static final Long serialVersionUID = 1L;

    @EmbeddedId
    private ContactPK contactPK;
    private String contact;

    @JsonIgnore
    @JoinColumn(name = "museum_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private MuseumEntity museum;

    @Transient
    @JsonIgnore
    @ToString.Exclude
    public final Mapper<Void, ContactModel> entity = (nothing) -> new ContactModel(
            this.getContactPK().getMuseumId(),
            this.getContactPK().getType(),
            this.getContact()
    );

    @Transient
    @JsonIgnore
    public static final Mapper<ContactModel, ContactEntity> model = model -> {
        var contact = new ContactEntity(model.contact());
        contact.setContactPK(new ContactPK(model.museumId(), model.type()));
        return contact;
    };

    public ContactEntity(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ContactEntity that = (ContactEntity) o;

        return new EqualsBuilder().append(contactPK, that.contactPK).append(contact, that.contact).append(museum, that.museum).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(contactPK).append(contact).append(museum).toHashCode();
    }

    @Override
    public String toString() {
        return "MuseumContact{" +
                "contactPK=" + contactPK +
                ", museumContact='" + contact + '\'' +
                '}';
    }
}

