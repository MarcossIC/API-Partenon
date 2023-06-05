package dev.partenon.user.domain.ports;

import dev.partenon.global.domain.EntityNotFoundException;
import dev.partenon.user.domain.User;
import dev.partenon.user.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class UserServicePort {
    @Autowired
    protected UserRepository repository;

    public abstract void save();

    public abstract User retrieveUserBy(String username) throws EntityNotFoundException;

    public abstract User retrieveUserForSome(String username, String email) throws EntityNotFoundException;

    public abstract Long retrieveMuseumId(String username);

}
