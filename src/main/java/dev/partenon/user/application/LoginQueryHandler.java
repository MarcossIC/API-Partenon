package dev.partenon.user.application;

import dev.partenon.global.domain.ports.query.QueryHandler;
import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.security.domain.AuthService;
import dev.partenon.security.domain.TokenType;
import dev.partenon.user.domain.models.User;
import dev.partenon.user.domain.models.UserQueryCommand;
import dev.partenon.user.domain.models.response.KeysApiModel;
import dev.partenon.user.domain.ports.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Maneja la query para Login Resource
 */
@Service
@RequiredArgsConstructor
public class LoginQueryHandler implements QueryHandler<UserQueryCommand, KeysApiModel> {
    private final AuthService service;
    private final AbstractJWT<User> jwtService;
    private final UserRepository repository;


    /**
     * Maneja la consulta para generar las llaves para usar la API
     *
     * @param query Datos de un usuario autorizado en la API
     * @return Modelo con los datos necesarios para las operacion
     */
    @Override
    public KeysApiModel handle(UserQueryCommand query) throws Exception {
        var userId = service.authenticate(query.getEmail(), query.getPassword());

        var user = new User(userId, query.getEmail(), query.getPassword());

        var accessToken = this.jwtService.generateToken(user, TokenType.ACESS);
        var refreshToken = this.jwtService.generateToken(user, TokenType.REFRESH);

        return new KeysApiModel(userId, accessToken, refreshToken);
    }
}
