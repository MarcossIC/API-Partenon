package dev.partenon.museumcontext.tours.domain;

import dev.partenon.global.domain.abstractcomponents.query.Query;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTourByMuseumQuery implements Query {
    private Long museumId;
}
