package dev.partenon.user.infrastructure.persistence;

import dev.partenon.user.domain.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user")
/**
 * Entidad Usuario
 */
public final class UserEntity implements Serializable {
    @Serial
    public static final Long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String userId;
    private String password;
    private String email;

    public static UserEntity map(User user) {
        var entity = new UserEntity();
        entity.setUserId(user.id());
        entity.setEmail(user.email());
        entity.setPassword(user.password());

        return entity;
    }

    public UserEntity(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
