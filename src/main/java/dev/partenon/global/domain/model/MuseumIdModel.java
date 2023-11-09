package dev.partenon.global.domain.model;

import dev.partenon.global.domain.ports.query.Response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class MuseumIdModel implements Response {
    private final String museumId;
}
