package dev.partenon.museums.plan.infrastructure.persistence;

import dev.partenon.museums.plan.domain.models.PlanModel;
import dev.partenon.museums.plan.domain.ports.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlanRepositoryAdapter implements PlanRepository {
    private final PlanJpaRepository jpaRepository;

    @Override
    public PlanModel savePlan(PlanModel model) {
        this.jpaRepository.save(PlanEntity.model.map(model));
        return model;
    }

    @Override
    public PlanModel findPlan(String museumId) {
        return this.jpaRepository.findById(museumId)
                .map(plan -> plan.entity.map(null))
                .orElse(new PlanModel(""));
    }
}
