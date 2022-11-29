package dev.partenon.user.application;

import dev.partenon.user.domain.User;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveSortingRepository<User, Long> {
    //Busca en base al username o el email
    Mono<User> findByUsernameOrEmail(String username, String email);
}
