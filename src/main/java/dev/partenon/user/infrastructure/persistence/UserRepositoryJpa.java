package dev.partenon.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repositorio con la implementacion de JpaRepository
 */
public interface UserRepositoryJpa extends JpaRepository<UserEntity, Long> {

    //Si existe un usuario con ese username o email devuelve true
    Boolean existsByEmail(String Email);

    //Busca en base al username o el email
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    @Query("SELECT m.museumId FROM User u JOIN u.museums m WHERE u.username = :username")
    Optional<Long> findMuseumIdByUsername(@Param("username") String username);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

}
