package dev.partenon.museumcontext.tours.application;

import dev.partenon.global.domain.abstractcomponents.query.QueryHandler;
import dev.partenon.museumcontext.tours.domain.GetTourByMuseumQuery;
import dev.partenon.museumcontext.tours.domain.TourResponse;
import dev.partenon.museumcontext.tours.domain.entity.MuseumTour;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**Maneja la Query echa por GetTourByNameResource*/
@Service
@AllArgsConstructor
public class GetTourByMuseumQueryHandler implements QueryHandler<GetTourByMuseumQuery, TourResponse> {
    private TourRepository repository;

    @Override
    public TourResponse handle(GetTourByMuseumQuery query) {
        return TourResponse.builder()
                .tours(Flux.just(query.getMuseumId())
                        .flatMap(x -> repository.findByTourPKMuseumId(x)
                                .subscribeOn(Schedulers.boundedElastic())
                                .switchIfEmpty(Flux.empty()))
                        .checkpoint().collectSortedList().block())
                .build();
    }
}
