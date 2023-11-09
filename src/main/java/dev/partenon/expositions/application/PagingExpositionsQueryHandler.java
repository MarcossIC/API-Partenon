package dev.partenon.expositions.application;

import dev.partenon.expositions.domain.actions.PagingExpositionsQuery;
import dev.partenon.expositions.domain.model.ExpositionModel;
import dev.partenon.expositions.domain.ports.ExpositionRepository;
import dev.partenon.global.domain.model.PagedResponse;
import dev.partenon.global.domain.ports.query.QueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * Maneja la Query para PagingExpositionResource
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class PagingExpositionsQueryHandler implements QueryHandler<PagingExpositionsQuery, PagedResponse<ExpositionModel>> {
    private final ExpositionRepository repository;

    @Override
    public PagedResponse<ExpositionModel> handle(PagingExpositionsQuery query) {
        return this.repository.pagingExpositionsByMuseumId(query.getMuseumId(), query.getInfoPagination());
    }
}
