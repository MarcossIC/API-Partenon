package dev.partenon.user.infrastructure.persistence;

import dev.partenon.global.domain.ports.Mapper;
import dev.partenon.user.domain.models.User;
import dev.partenon.user.domain.ports.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class UserRepositoryAdapter implements UserRepository {
    private final UserRepositoryJpa repositoryJpa;
    private final PasswordEncoder encoder;

    private final Mapper<User, UserEntity> modelToEntity = user -> new UserEntity(user.id(), user.email(), user.password());
    private final Mapper<UserEntity, User> entityToModel = user -> new User(user.getUserId(), user.getEmail(), user.getPassword());

    @Override
    public User save(User user) {
        Optional.ofNullable(user)
                .map(modelToEntity::map)
                .ifPresent(entity -> {
                    entity.setPassword(encoder.encode(entity.getPassword()));
                    repositoryJpa.save(entity);
                });

        return Optional.ofNullable(user).orElseThrow(() -> new IllegalArgumentException("User in user save repository is null"));
    }

    @Override
    public Boolean isRegisteredUser(String email) {
        return repositoryJpa.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repositoryJpa.findByEmail(email).map(this.entityToModel::map);
    }
}
