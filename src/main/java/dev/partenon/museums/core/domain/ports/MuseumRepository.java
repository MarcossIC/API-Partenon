package dev.partenon.museums.core.domain.ports;

import dev.partenon.global.domain.model.InfoPagination;
import dev.partenon.global.domain.model.PagedResponse;
import dev.partenon.museums.core.domain.models.Museum;
import dev.partenon.museums.core.domain.models.projections.MuseumSummaryProjection;
import dev.partenon.museums.core.domain.models.projections.PaginatedMuseumProjection;

import java.util.Optional;

public interface MuseumRepository {
    Museum saveMuseum(Museum model);

    Optional<Museum> searchMuseumByUserIdAndName(String userId, String name);

    Optional<MuseumSummaryProjection> getMuseumSummary(String museumId);

    PagedResponse<PaginatedMuseumProjection> pageMuseumByName(InfoPagination infoPagination, String term);

    Boolean museumExistsTo(String museumId, String userId);
}
