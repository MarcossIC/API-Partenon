package dev.partenon.user.application.handlers;

import dev.partenon.global.domain.abstractcomponents.query.QueryHandler;
import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.user.domain.InitQuery;
import dev.partenon.user.domain.model.response.MuseumIdModel;
import dev.partenon.user.domain.ports.UserMapperPort;
import dev.partenon.user.domain.ports.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Maneja la query para InitResource
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InitQueryHandler implements QueryHandler<InitQuery, MuseumIdModel> {
    private final AbstractJWT jwtProvider;
    private final UserServicePort service;
    private final UserMapperPort mapper;

    /**
     * Recupera el ID del museo del usuario al iniciar el sitio.
     *
     * @param query Query que contiene los datos del token de autenticación.
     * @return Objeto MuseumId que representa el ID del museo asociado al usuario.
     */
    @Override
    public MuseumIdModel handle(InitQuery query) {
        var key = Optional.ofNullable(query.getBearerToken())
                .filter(jwtProvider::validateBearerToken)
                .map(jwtProvider::getToken)
                .orElse("");

        var username = Optional.ofNullable(key)
                .filter(jwtProvider::validateContentToken)
                .map(jwtProvider::getUsername)
                .orElse("");

        var value = service.retrieveMuseumId(username);

        return mapper.mapMuseumIdModel(value);
    }
}
