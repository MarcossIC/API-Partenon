package dev.partenon.museumcontext.score.doamin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
/**Clave primaria de la entidad Score*/
public class MuseumScorePK implements Serializable {
    public static final Long serialVersionUID = 1L;

    public MuseumScorePK(Long museumId, Long userEmailId) {
        super();
        this.museumId = museumId;
        this.userEmailId = userEmailId;
    }

    protected Long museumId;
    protected Long userEmailId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MuseumScorePK that = (MuseumScorePK) o;

        return new EqualsBuilder().append(museumId, that.museumId).append(userEmailId, that.userEmailId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(museumId).append(userEmailId).toHashCode();
    }
}
