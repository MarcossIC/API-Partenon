package dev.partenon.user.domain.model.response;

import dev.partenon.global.domain.abstractcomponents.query.Response;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MuseumIdModel implements Response {
    private String museumId;
}
