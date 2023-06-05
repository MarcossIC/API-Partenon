package dev.partenon.museumcontext.core.doamin.query;

import dev.partenon.global.domain.abstractcomponents.query.Query;
import dev.partenon.global.domain.model.Page;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingByNameQuery implements Query {
    private String charactersNames;
    private Page page;
}
