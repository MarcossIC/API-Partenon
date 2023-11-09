package dev.partenon.user.domain.models.querys;

import dev.partenon.global.domain.ports.query.Query;
import dev.partenon.user.domain.models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class RefreshQuery implements Query {
    private final User user;
}
