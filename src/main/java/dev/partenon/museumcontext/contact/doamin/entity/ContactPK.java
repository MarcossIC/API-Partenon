package dev.partenon.museumcontext.contact.doamin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.museumcontext.contact.doamin.SocialMediaType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
/**Clave primaria del Contacto del museo*/
public class ContactPK implements Serializable {
    public static final Long serialVersionUID = 1L;

    @JsonIgnore
    protected Long museumId;
    protected SocialMediaType type;

    public ContactPK(Long museumId, SocialMediaType type) {
        super();
        this.museumId = museumId;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ContactPK contactPK = (ContactPK) o;

        return new EqualsBuilder().append(museumId, contactPK.museumId).append(type, contactPK.type).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(museumId).append(type).toHashCode();
    }
}
