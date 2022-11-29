package dev.partenon.museumcontext.score.doamin;

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
/**Entidad Score*/
public class MuseumScore implements Serializable {
    public static final Long serialVersionUID = 1L;

    private MuseumScorePK scorePK;
    private Integer score;
    private Museum museum;
    private UserEmail userEmail;

    public MuseumScore(Long museumId, Long userEmailId, Integer score) {
        super();
        this.scorePK = new MuseumScorePK(museumId, userEmailId);
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MuseumScore that = (MuseumScore) o;

        return new EqualsBuilder().append(scorePK, that.scorePK).append(score, that.score).append(museum, that.museum).append(userEmail, that.userEmail).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(scorePK).append(score).append(museum).append(userEmail).toHashCode();
    }
}
