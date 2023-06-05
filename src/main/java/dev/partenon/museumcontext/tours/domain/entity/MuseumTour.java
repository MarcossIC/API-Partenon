package dev.partenon.museumcontext.tours.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
/**Entidad Tour*/
public class MuseumTour implements Serializable {
    public static final Long serialVersionUID = 1L;

    private TourPK tourPK;

    private String description;
    private Integer duration;
    @JsonIgnore
    private Museum museum;

    public MuseumTour(Long museumId, String tourName) {
        super();
        this.tourPK = new TourPK(museumId, tourName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MuseumTour that = (MuseumTour) o;

        return new EqualsBuilder().append(tourPK, that.tourPK).append(museum, that.museum).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(tourPK).append(museum).toHashCode();
    }
}
