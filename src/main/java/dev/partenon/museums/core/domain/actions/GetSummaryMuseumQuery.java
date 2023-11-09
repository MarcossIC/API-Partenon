package dev.partenon.museums.core.domain.actions;

import dev.partenon.global.domain.ports.query.Query;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class GetSummaryMuseumQuery implements Query {
    private final String id;
}
