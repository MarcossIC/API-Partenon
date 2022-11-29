package dev.partenon.user.domain;

import dev.partenon.global.domain.abstractcomponents.query.Query;
import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Mono;

@Data
@Builder
public class InitQuery implements Query {
    private String authToken;
}
