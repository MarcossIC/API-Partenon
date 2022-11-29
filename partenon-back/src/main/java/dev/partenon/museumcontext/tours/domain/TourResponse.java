package dev.partenon.museumcontext.tours.domain;

import dev.partenon.global.domain.abstractcomponents.query.Response;
import dev.partenon.museumcontext.tours.domain.entity.MuseumTour;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class TourResponse implements Response, Serializable {
    private List<MuseumTour> tours;
}
