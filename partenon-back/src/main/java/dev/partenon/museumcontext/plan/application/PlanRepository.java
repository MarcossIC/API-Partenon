package dev.partenon.museumcontext.plan.application;

import dev.partenon.museumcontext.plan.doamin.MuseumPlan;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface PlanRepository extends ReactiveSortingRepository<MuseumPlan, Long> {
    /**Comprueba si el plano existe*/
    Boolean existsByPlanId(Long id);
}
