package dev.partenon.museumcontext.tours.domain;

import dev.partenon.global.domain.abstractcomponents.query.Query;
import dev.partenon.museumcontext.tours.domain.entity.MuseumTour;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetTourByMuseumQuery implements Query {
    private Long museumId;
}
