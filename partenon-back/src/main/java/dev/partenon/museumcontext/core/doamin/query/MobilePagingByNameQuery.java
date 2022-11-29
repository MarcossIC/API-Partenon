package dev.partenon.museumcontext.core.doamin.query;

import dev.partenon.global.domain.abstractcomponents.query.Query;
import dev.partenon.global.domain.model.Page;
import dev.partenon.global.domain.model.PagedResponse;
import dev.partenon.museumcontext.core.doamin.models.MobileSummaryMuseumProjection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MobilePagingByNameQuery implements Query{
    private String charactersNames;
    private Page page;
}
