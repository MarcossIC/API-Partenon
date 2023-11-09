package dev.partenon.museums.plan.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanJpaRepository extends JpaRepository<PlanEntity, String> {
    /**
     * Comprueba si el plano existe
     */
    Boolean existsByPlanId(Long id);
}
