package dev.partenon.museums.schedule.infrastructure.persistence;

import dev.partenon.museums.schedule.infrastructure.persistence.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, Long> {

    /**
     * Comprueba que el horario de apertura xista
     */
    Boolean existsByOpeningHoursId(Long id);
}
