package dev.partenon.museums.contact.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.museums.contact.domain.models.SocialMediaType;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@Embeddable
public class ContactPK implements Serializable {
    public static final Long serialVersionUID = 1L;

    @JsonIgnore
    @Column(name = "museum_id", nullable = false)
    protected String museumId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false,
            columnDefinition = "enum('TWITTER', 'FACEBOOK', 'WSP', 'EMAIL', 'INSTAGRAM')")
    protected SocialMediaType type;

    public ContactPK(String museumId, SocialMediaType type) {
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
