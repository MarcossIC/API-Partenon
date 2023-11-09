package dev.partenon.user.application;


import dev.partenon.global.domain.ports.query.QueryHandler;
import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.security.domain.TokenType;
import dev.partenon.user.domain.models.querys.RefreshQuery;
import dev.partenon.user.domain.models.response.KeysApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Query que maneja el handler del Refresh Resource
 */
@Service
@RequiredArgsConstructor
public class RefreshQueryHandler implements QueryHandler<RefreshQuery, KeysApiModel> {
    private final AbstractJWT jwtService;

    @Override
    public KeysApiModel handle(RefreshQuery query) throws Exception {
        var accessToken = this.jwtService.generateToken(query.getUser(), TokenType.ACESS);
        var refreshToken = this.jwtService.generateToken(query.getUser(), TokenType.REFRESH);

        return new KeysApiModel(query.getUser().id(), accessToken, refreshToken);

    }

}
