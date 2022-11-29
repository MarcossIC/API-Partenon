package dev.partenon.user.domain.model;

import dev.partenon.global.domain.abstractcomponents.query.Response;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenUpdated implements Response {
    private String accessToken;
    private String refreshToken;
    private String museumId;
}
