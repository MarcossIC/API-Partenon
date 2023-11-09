package dev.partenon.expositions.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpositionJpaRepository extends JpaRepository<ExpositionsEntity, String> {
    /**
     * Paginar expociciones de un museo
     */
    Page<ExpositionsEntity> findByMuseumId(String museumId, Pageable pageable);

    Optional<ExpositionsEntity> findByExpositionId(Long expositionId);
}
