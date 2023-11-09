package dev.partenon.museums.core.infrastructure.persistence;

import dev.partenon.museums.core.domain.models.Museum;
import dev.partenon.museums.core.domain.models.projections.MuseumSummaryProjection;
import dev.partenon.museums.core.domain.models.projections.PaginatedMuseumProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MuseumJpaRepository extends JpaRepository<MuseumEntity, Long> {

    Optional<Museum> findByMuseumId(Long museumId);

    Optional<Museum> findByUserEmail(String email);

    Optional<MuseumEntity> findByUserEntityUserIdAndName(String userId, String name);

    @Query(value = """
            SELECT *
            FROM museum AS m
            WHERE m.id = :museumId AND m.user_id = :userId
            """, nativeQuery = true)
    Optional<MuseumEntity> findByMuseumIdAndUserId(String museumId, String userId);

    @Query(value = """
            SELECT m.description, mp.building_plan, mc.contact, mc.type, s.day, s.shift, s.opening, s.close
            FROM museum m
            LEFT JOIN schedule AS s ON m.id = s.museum_id
            LEFT JOIN museum_contact AS mc ON m.id = mc.museum_id
            LEFT JOIN museum_plan AS mp ON m.id = mp.museum_id
            WHERE m.id = :id
            """, nativeQuery = true)
    Optional<MuseumSummaryProjection> searchSummaryById(String id);


    @Query(value = """
            SELECT m.id, m.name, m.province, m.city, m.street, m.address_number AS addressNumber, mb.banner
            FROM museum AS m
            LEFT JOIN museum_banner mb ON m.id = mb.museum_id
            WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :term, '%'))
            """, nativeQuery = true)
    Page<PaginatedMuseumProjection> searchByName(@Param("termName") String termName, Pageable pageable);

    @Query("SELECT new dev.partenon.museumcontext.core.domain.models.response.PaginatedMuseumProjection" +
            "(m.id, m.name, m.province, m.city, m.street, m.addressNumber, m.museumBanner.image) " +
            "FROM Museum m")
    Page<PaginatedMuseumProjection> paginatedMuseums(Pageable pageable);
}
