package dev.partenon.user.domain.models.response;

import dev.partenon.global.domain.ports.query.Response;

public record KeysApiModel(String userId, String accessToken, String refreshToken) implements Response {
}
