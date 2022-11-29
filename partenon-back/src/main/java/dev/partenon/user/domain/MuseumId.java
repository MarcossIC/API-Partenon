package dev.partenon.user.domain;

import dev.partenon.global.domain.abstractcomponents.query.Response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MuseumId implements Response {
    private String museumId;
}
