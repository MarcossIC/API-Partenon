package dev.partenon.user.application.handlers;

import dev.partenon.global.domain.abstractcomponents.query.QueryHandler;
import dev.partenon.security.infrastructure.components.CredentialService;
import dev.partenon.user.domain.LoginQuery;
import dev.partenon.user.domain.model.response.KeysApiModel;
import dev.partenon.user.domain.ports.UserMapperPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Maneja la query para Login Resource
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginQueryHandler implements QueryHandler<LoginQuery, KeysApiModel> {
    private final CredentialService service;
    private final UserMapperPort mapper;

    /**
     * Maneja la consulta para generar las llaves para usar la API
     *
     * @param query Datos de un usuario autorizado en la API
     * @return Modelo con los datos necesarios para las operacion
     */
    @Override
    public KeysApiModel handle(LoginQuery query) throws Exception {
        var res = service.generateCredentials(query.getUsernameOrEmail(), query.getPassword());
        return mapper.mapKeysAPI(res);
    }
}
