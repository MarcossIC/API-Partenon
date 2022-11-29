package dev.partenon.user.domain;

import dev.partenon.global.domain.abstractcomponents.query.Query;
import dev.partenon.user.domain.model.TokenUpdated;
import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Mono;

@Data
@Builder
public class RefreshQuery implements Query {
    private String headerToken;
}
