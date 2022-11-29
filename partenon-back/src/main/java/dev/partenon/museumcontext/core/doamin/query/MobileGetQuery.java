package dev.partenon.museumcontext.core.doamin.query;

import dev.partenon.global.domain.abstractcomponents.query.Query;

import dev.partenon.museumcontext.core.doamin.models.MobilMuseumProjection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MobileGetQuery implements Query {
    private Long museumId;
}
