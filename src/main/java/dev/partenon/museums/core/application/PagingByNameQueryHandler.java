package dev.partenon.museums.core.application;

import dev.partenon.global.domain.model.PagedResponse;
import dev.partenon.global.domain.ports.query.QueryHandler;
import dev.partenon.museums.core.domain.actions.PagingByNameQuery;
import dev.partenon.museums.core.domain.models.projections.PaginatedMuseumProjection;
import dev.partenon.museums.core.domain.ports.MuseumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagingByNameQueryHandler implements QueryHandler<PagingByNameQuery, PagedResponse<PaginatedMuseumProjection>> {
    private final MuseumRepository repository;

    @Override
    public PagedResponse<PaginatedMuseumProjection> handle(PagingByNameQuery query) throws Exception {
        return this.repository.pageMuseumByName(query.getInfoPagination(), query.getTerm());
    }
}
