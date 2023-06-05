package dev.partenon.user.domain;

import dev.partenon.global.domain.abstractcomponents.query.Query;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class InitQuery implements Query {
    private final String bearerToken;
}
