package dev.partenon.user.application;

import dev.partenon.global.domain.EntityNotFoundException;
import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.user.domain.User;
import dev.partenon.user.domain.ports.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService extends UserServicePort {

    private AbstractJWT jwt;

    @Override
    public void save(User user) {
        this.repository.save(user);
    }

    @Override
    public User retrieveUserByUsername(String username)  {
        Optional<User> user = this.repository.findByUsername(username);

        return user.orElse(null);
    }
    @Override
    public User retrieveUserByEmail(String email)  {
        Optional<User> user = this.repository.findByEmail(email);

        return user.orElse(null);
    }

    @Override
    public User retrieveUserForSome(String username, String email) throws EntityNotFoundException {
        Optional<User> user = this.repository.findByUsernameOrEmail(username, email);
        return user.orElse(null);
    }

    @Override
    public Long retrieveMuseumId(String username) {
        var id = this.repository.findMuseumIdByUsername(username);
        return (id.isEmpty()) ? 0L : id.get();
    }
}
