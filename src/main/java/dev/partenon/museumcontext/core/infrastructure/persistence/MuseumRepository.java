package dev.partenon.museumcontext.core.infrastructure.persistence;

import dev.partenon.museumcontext.core.doamin.Museum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MuseumRepository extends ReactiveSortingRepository<Museum, Long> {
    /**
     * Busca un museo por ID
     */
    Mono<Museum> findByMuseumId(Long museumId);

    /**
     * Busca un museo por username o email
     */
    Mono<Museum> findByUserUsernameOrUserEmail(String username, String email);

    /**
     * Pagina los museos segun el nombre que le pases
     */
    @Query("SELECT m FROM Museum m WHERE " +
            "LOWER(m.museumName) LIKE LOWER(CONCAT('%',:termName,'%'))")
    Flux<Museum> searchByName(@Param("termName") String termName, Pageable pageable);
}
