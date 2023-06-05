package dev.partenon.user.application;

import dev.partenon.global.domain.EntityNotFoundException;
import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.user.domain.User;
import dev.partenon.user.domain.ports.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService extends UserServicePort {

    private AbstractJWT jwt;

    @Override
    public void save() {

    }

    @Override
    public User retrieveUserBy(String username) throws EntityNotFoundException {
        Optional<User> user = this.repository.findByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User", username);
        }
        return user.get();
    }

    @Override
    public User retrieveUserForSome(String username, String email) throws EntityNotFoundException {
        Optional<User> user = this.repository.findByUsernameOrEmail(username, email);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User", username);
        }

        return user.get();
    }

    @Override
    public Long retrieveMuseumId(String username) {
        var id = this.repository.findMuseumIdByUsername(username);
        return (id.isEmpty()) ? 0L : id.get();
    }
}
