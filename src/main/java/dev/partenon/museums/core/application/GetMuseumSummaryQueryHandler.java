package dev.partenon.museums.core.application;

import dev.partenon.global.domain.exceptions.ContentNotFoundException;
import dev.partenon.global.domain.ports.query.QueryHandler;
import dev.partenon.museums.core.domain.actions.GetSummaryMuseumQuery;
import dev.partenon.museums.core.domain.models.projections.MuseumSummaryProjection;
import dev.partenon.museums.core.domain.ports.MuseumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMuseumSummaryQueryHandler implements QueryHandler<GetSummaryMuseumQuery, MuseumSummaryProjection> {
    private final MuseumRepository repository;

    @Override
    public MuseumSummaryProjection handle(GetSummaryMuseumQuery query) throws Exception {
        if (query.getId() == null) throw new IllegalArgumentException("Museum ID cannot be null");

        var summary = repository.getMuseumSummary(query.getId());
        if (summary.isEmpty())
            throw new ContentNotFoundException("No se ha encontrado Museo con ID " + query.getId(), "GetMuseumSummaryQueryHandler");

        return summary.get();
    }
}
