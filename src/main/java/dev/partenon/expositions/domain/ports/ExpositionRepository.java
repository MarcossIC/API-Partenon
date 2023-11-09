package dev.partenon.expositions.domain.ports;

import dev.partenon.expositions.domain.model.ExpositionModel;
import dev.partenon.global.domain.model.InfoPagination;
import dev.partenon.global.domain.model.PagedResponse;

import java.util.List;

public interface ExpositionRepository {
    List<ExpositionModel> expositionByMuseumId(final String museumId);

    PagedResponse<ExpositionModel> pagingExpositionsByMuseumId(final String museumId, InfoPagination page);

    ExpositionModel saveExposition(ExpositionModel model);
}
