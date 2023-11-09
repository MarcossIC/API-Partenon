package dev.partenon.user.domain.models.querys;

import dev.partenon.global.domain.ports.query.Query;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class InitQuery implements Query {
    private final String bearerToken;
}
