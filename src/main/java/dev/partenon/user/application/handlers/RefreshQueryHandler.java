package dev.partenon.user.application.handlers;


import dev.partenon.global.domain.abstractcomponents.query.QueryHandler;
import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.user.domain.RefreshQuery;
import dev.partenon.user.domain.User;
import dev.partenon.user.domain.model.response.KeysApiModel;
import dev.partenon.user.domain.ports.UserMapperPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Query que maneja el handler del Refresh Resource
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RefreshQueryHandler implements QueryHandler<RefreshQuery, KeysApiModel> {
    private final AbstractJWT jwtProvider;
    private final UserMapperPort mapper;

    @Override
    public KeysApiModel handle(RefreshQuery query) throws Exception {
        var key = Optional.ofNullable(query.getBearerToken())
                .filter(jwtProvider::validateBearerToken)
                .map(jwtProvider::getToken)
                .orElse("");

        var username = Optional.ofNullable(key)
                .filter(jwtProvider::validateContentToken)
                .map(jwtProvider::getUsername)
                .orElse("");
        var id = Optional.ofNullable(key)
                .filter(jwtProvider::validateContentToken)
                .map(jwtProvider::getUserId)
                .orElse("");


        return mapper.mapKeysAPI(new User(username, id));

    }

}
