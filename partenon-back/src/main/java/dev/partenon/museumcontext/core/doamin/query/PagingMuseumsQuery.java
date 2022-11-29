package dev.partenon.museumcontext.core.doamin.query;

import dev.partenon.global.domain.abstractcomponents.query.Query;
import dev.partenon.global.domain.model.Page;
import dev.partenon.global.domain.model.PagedResponse;
import dev.partenon.museumcontext.core.doamin.models.MuseumProjection;
import lombok.Data;

@Data
public class PagingMuseumsQuery implements Query{
    private final Page page;

    /**
     * Builder de la Query
     */
    public static class Builder {
        private Page page;

        public static PagingMuseumsQuery.Builder getInstance() {
            return new PagingMuseumsQuery.Builder();
        }

        public PagingMuseumsQuery.Builder page(Page page) {
            this.page = page;
            return this;
        }

        public PagingMuseumsQuery build() {
            return new PagingMuseumsQuery(page);
        }
    }
}
