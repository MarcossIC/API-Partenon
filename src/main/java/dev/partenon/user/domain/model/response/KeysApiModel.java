package dev.partenon.user.domain.model.response;

import dev.partenon.global.domain.abstractcomponents.query.Response;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
public  class KeysApiModel implements Response {
    private  String museumId;
    private  String accessToken;
    private  String refreshToken;
}
