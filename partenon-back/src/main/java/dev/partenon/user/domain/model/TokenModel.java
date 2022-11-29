package dev.partenon.user.domain.model;

import dev.partenon.global.domain.abstractcomponents.query.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public final class TokenModel implements Response {
    private String museumId;
    private String accessToken;
    private String refreshToken;
}
