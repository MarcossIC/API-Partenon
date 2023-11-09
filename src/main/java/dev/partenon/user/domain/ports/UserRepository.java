package dev.partenon.user.domain.ports;

import dev.partenon.user.domain.models.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Boolean isRegisteredUser(String email);

    Optional<User> findByEmail(String email);

}
