package dev.partenon.user.application.handlers;

import dev.partenon.global.domain.abstractcomponents.query.QueryHandler;
import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.security.infrastructure.components.JWTProvider;
import dev.partenon.user.application.UserRepository;
import dev.partenon.user.domain.InitQuery;
import dev.partenon.user.domain.MuseumId;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**Maneja la query para InitResource*/
@Service
@AllArgsConstructor
public class InitQueryHandler implements QueryHandler<InitQuery, MuseumId> {
    @Autowired
    private final AbstractJWT jwt;
    @Autowired
    private final UserRepository repository;

    @Override
    public MuseumId handle(InitQuery query) {
        var token = jwt.validateAuthorizationToken(query.getAuthToken());

        if(StringUtils.hasText(token)) return null;
        //Con los datos del token pido el ID del museo a la base de datos y lo devuelvo
       return Mono.just(jwt.getSubjectUsername(token))
               .flatMap(username ->
                       repository.findByUsernameOrEmail(username, username)
                               .subscribeOn(Schedulers.parallel())
                               .delayElement(Duration.ofMillis(2))
                               .switchIfEmpty(Mono.error(new Exception("Este usuario no existe"))))
               .map(user -> MuseumId.builder().museumId(user.getMuseum().getMuseumId().toString()).build())
               .subscribeOn(Schedulers.single()).block();

    }


}
