package dev.partenon.user.infrastructure.persistence;

import dev.partenon.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repositorio con la implementacion de JpaRepository
 */
public interface UserRepository extends JpaRepository<User, Long> {
    //Busca en base al username o el email
    Optional<User> findByUsernameOrEmail(String username, String email);

    @Query("SELECT m.museumId FROM User u JOIN u.museums m WHERE u.username = :username")
    Optional<Long> findMuseumIdByUsername(@Param("username") String username);

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}
