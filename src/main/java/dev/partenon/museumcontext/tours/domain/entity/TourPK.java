package dev.partenon.museumcontext.tours.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
/**Clave primaria de la entidad Tour*/
public class TourPK implements Serializable {
    public static final Long serialVersionUID = 1L;

    @JsonIgnore
    protected Long museumId;
    protected String tourName;

    public TourPK(Long museumId, String tourName) {
        super();
        this.museumId = museumId;
        this.tourName = tourName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TourPK tourPK = (TourPK) o;

        return new EqualsBuilder().append(museumId, tourPK.museumId).append(tourName, tourPK.tourName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(museumId).append(tourName).toHashCode();
    }
}
