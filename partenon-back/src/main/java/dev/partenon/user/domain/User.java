package dev.partenon.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.museumcontext.core.doamin.SaveMuseumAndUserCommand;
import dev.partenon.museumcontext.core.doamin.Museum;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
/**Entidad Usuario*/
public final class User implements Serializable {
    public static final Long serialVersionUID = 1L;

    private Long userId;
    private String username;
    private String password;
    private String email;

    @JsonIgnore
    private Museum museum;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    public User(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public static User create(SaveMuseumAndUserCommand command, PasswordEncoder passwordEncoder){
        var user = new User();
        user.setEmail(command.getEmail());
        String password = passwordEncoder.encode(command.getPassword());
        user.setPassword(password);
        user.setUsername(command.getUsername());
        return user;
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
