package dev.partenon.museums.core.domain.models.projections;

import dev.partenon.global.domain.ports.query.Response;

import java.util.List;

public interface MuseumSummaryProjection extends Response {
    String getDescription();

    List<Schedule> getOpeningHours();

    List<MuseumContactProjection> getContacts();

    String getMuseumPlan();

}
