package dev.partenon.museumcontext.core.application.handlers.pc;

import dev.partenon.global.domain.abstractcomponents.query.QueryHandler;
import dev.partenon.museumcontext.core.application.MuseumRepository;
import dev.partenon.museumcontext.core.doamin.query.GetQuery;
import dev.partenon.museumcontext.core.doamin.Museum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


@Service
/**Maneja la Query de GetMuseumResource*/
public class GetMuseumQueryHandler implements QueryHandler<GetQuery, Museum> {
    @Autowired
    private MuseumRepository repository;

    @Override
    public Museum handle(GetQuery query) {
        return Mono.just(query.getMuseumId())
                .flatMap(repository::findByMuseumId)
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new RuntimeException("ID No registrado") ) ).block() ;
    }
}
