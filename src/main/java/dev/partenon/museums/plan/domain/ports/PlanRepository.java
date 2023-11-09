package dev.partenon.museums.plan.domain.ports;

import dev.partenon.museums.plan.domain.models.PlanModel;

public interface PlanRepository {
    PlanModel savePlan(PlanModel model);

    PlanModel findPlan(String museumId);
}
