package dev.partenon.museums.core.infrastructure.web;

import dev.partenon.global.domain.ports.query.QueryBus;
import dev.partenon.museums.core.domain.actions.GetSummaryMuseumQuery;
import dev.partenon.museums.core.domain.models.projections.MuseumSummaryProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GetMuseumSummaryResource {
    private final QueryBus queryBus;

    @GetMapping("/museums/{id}")
    public HttpEntity<MuseumSummaryProjection> pagingMuseum(
            @RequestParam(value = "id") String id) throws Exception {

        var query = GetSummaryMuseumQuery.builder()
                .id(id)
                .build();

        var response = (MuseumSummaryProjection) queryBus.ask(query);

        return ResponseEntity.ok(response);
    }
}
