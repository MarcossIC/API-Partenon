package dev.partenon.user.domain.model.response;

import dev.partenon.global.domain.abstractcomponents.query.Response;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
public final class KeysApiModel implements Response {
    private final String museumId;
    private final String accessToken;
    private final String refreshToken;
}
