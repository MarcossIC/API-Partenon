package dev.partenon.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.museumcontext.core.doamin.Museum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
/**
 * Entidad Usuario
 */
public final class User implements Serializable {
    @Serial
    public static final Long serialVersionUID = 1L;

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private String email;

    @JsonIgnore
    private Museum museum;

    public User(String username, String id) {
        this.username = username;
        this.userId = Long.parseLong(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
