package dev.partenon.museums.plan.domain.models;

public record PlanModel(String museumId, String plan) {
    public PlanModel(String plan) {
        this(null, plan);
    }
}
