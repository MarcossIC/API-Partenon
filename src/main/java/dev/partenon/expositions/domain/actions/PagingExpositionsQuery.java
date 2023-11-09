package dev.partenon.expositions.domain.actions;

import dev.partenon.global.domain.model.InfoPagination;
import dev.partenon.global.domain.ports.query.Query;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingExpositionsQuery implements Query {
    private final String museumId;
    private final InfoPagination infoPagination;
}
