package dev.partenon.museumcontext.score.doamin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/**Entidad User Email*/
public class UserEmail implements Serializable {
    public static final Long serialVersionUID = 1L;
    private Long userEmailId;
    private String email;

    @JsonBackReference
    private List<MuseumScore> scoresGiven;

    public static UserEmail create(String email) {
        var user = new UserEmail();
        user.setEmail(email);
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserEmail userEmail = (UserEmail) o;

        return new EqualsBuilder().append(userEmailId, userEmail.userEmailId).append(email, userEmail.email).append(scoresGiven, userEmail.scoresGiven).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(userEmailId).append(email).append(scoresGiven).toHashCode();
    }
}
