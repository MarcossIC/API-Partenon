package dev.partenon.museumcontext.core.application.handlers.mobil;

import dev.partenon.global.domain.abstractcomponents.query.QueryHandler;

import dev.partenon.museumcontext.core.application.MuseumRepository;
import dev.partenon.museumcontext.core.doamin.models.MobilMuseumProjection;
import dev.partenon.museumcontext.core.doamin.query.MobileGetQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class MobilGetMuseumQueryHandler implements QueryHandler<MobileGetQuery, MobilMuseumProjection> {
    @Autowired
    private MuseumRepository repository;

    @Override
    public MobilMuseumProjection handle(MobileGetQuery query) throws Exception {
        return Mono.just(query.getMuseumId())
                .flatMap(repository::findByMuseumId)
                .subscribeOn(Schedulers.immediate())
                .switchIfEmpty(Mono.error(new RuntimeException("ID No registrado") ) )
                .map(MobilMuseumProjection::create).block();
    }
}
