package dev.partenon.museums.core.domain.actions;

import dev.partenon.global.domain.model.InfoPagination;
import dev.partenon.global.domain.ports.query.Query;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingByNameQuery implements Query {
    private String term;
    private InfoPagination infoPagination;
}
