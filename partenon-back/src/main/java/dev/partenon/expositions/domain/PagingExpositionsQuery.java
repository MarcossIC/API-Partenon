package dev.partenon.expositions.domain;

import dev.partenon.global.domain.abstractcomponents.query.Query;
import dev.partenon.global.domain.model.Page;
import dev.partenon.global.domain.model.PagedResponse;
import lombok.Data;

@Data
public class PagingExpositionsQuery implements Query {
    private final Long museumId;
    private final Page page;

    /**
     * Builder de la Query
     */
    public static class Builder {
        private Page page;
        private Long museumId;

        public static PagingExpositionsQuery.Builder getInstance() {
            return new PagingExpositionsQuery.Builder();
        }

        public PagingExpositionsQuery.Builder page(Page page) {
            this.page = page;
            return this;
        }
        public PagingExpositionsQuery.Builder museumId(String museumId) {
            this.museumId = Long.valueOf(museumId);
            return this;
        }

        public PagingExpositionsQuery build() {
            return new PagingExpositionsQuery(museumId,page);
        }
    }
}
