package dev.partenon.museums.core.domain.actions;

import dev.partenon.global.domain.model.InfoPagination;
import dev.partenon.global.domain.ports.query.Query;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingMuseumsQuery implements Query {
    private final InfoPagination infoPagination;
}
