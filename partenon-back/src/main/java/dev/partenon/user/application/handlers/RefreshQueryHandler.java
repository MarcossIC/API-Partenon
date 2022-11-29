package dev.partenon.user.application.handlers;


import dev.partenon.global.domain.abstractcomponents.query.QueryHandler;
import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.security.domain.CustomUserDetails;
import dev.partenon.security.infrastructure.components.JWTProvider;
import dev.partenon.user.domain.RefreshQuery;
import dev.partenon.user.domain.User;
import dev.partenon.user.domain.model.TokenUpdated;
import dev.partenon.security.infrastructure.components.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**Query que maneja el handler del Refresh Resource*/
@Service
@AllArgsConstructor
public class RefreshQueryHandler implements QueryHandler<RefreshQuery, TokenUpdated> {
    private final AbstractJWT jwtProvider;
    private final CustomUserDetailsService service;

    @Override
    public TokenUpdated handle(RefreshQuery query) throws Exception {
        return Mono.just(query.getHeaderToken())
                .map(jwtProvider::validateAuthorizationToken)
                .map(jwtProvider::getSubjectUsername)
                .map(username-> (CustomUserDetails) service.findByUsername(username).switchIfEmpty(Mono.error(new RuntimeException("Username no registrado"))).block() )
                .map(user-> TokenUpdated.builder()
                        .accessToken(jwtProvider.createAccessToken(
                                new User(user.getUserId(), user.getUsername()), "http://localhost:8080/api/auth/refresh"))
                        .refreshToken(jwtProvider.createRefreshToken(
                                new User(user.getUserId(), user.getUsername())))
                        .museumId(String.valueOf(user.getMuseumId()))
                        .build())
                .checkpoint().block();

    }

}
