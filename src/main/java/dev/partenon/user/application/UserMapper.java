package dev.partenon.user.application;

import dev.partenon.museumcontext.core.doamin.Museum;
import dev.partenon.user.domain.RegisterCommand;
import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.user.domain.User;
import dev.partenon.user.domain.model.response.KeysApiModel;
import dev.partenon.user.domain.model.response.MuseumIdModel;
import dev.partenon.user.domain.ports.UserMapperPort;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserMapper implements UserMapperPort {
    private final AbstractJWT jwt;
    private final PasswordEncoder passwordEncoder;

    /**
     *  Mapea el Usuario al modelo KeysApiModel
     *
     * @param user Entidad User
     */
    public KeysApiModel mapKeysAPI(User user) {
        return KeysApiModel.builder()
                .accessToken(jwt.createAccessToken(user, "http://localhost:8080/api/refresh"))
                .refreshToken(jwt.createRefreshToken(user))
                .museumId(String.valueOf(user.getMuseum().getMuseumId()))
                .build();
    }

    /**
     * Mapea el ID del Museo al modelo MuseumId
     *
     * @param value ID del Museo
     */
    public MuseumIdModel mapMuseumIdModel(Long value) {
        return (value == 0)
                ? MuseumIdModel.builder().museumId("ID undefined").build()
                : MuseumIdModel.builder().museumId(value.toString()).build();
    }

    /**
     *  Mapea el comando de crear cuenta
     *
     * @param command
     * @return
     */
    public User mapUser(RegisterCommand command) {
        var user = new User();
        user.setEmail(command.getEmail());
        String password = passwordEncoder.encode(command.getPassword());
        user.setPassword(password);
        user.setUsername(command.getUsername());

        var museum = new Museum();
        museum.setMuseumName(StringUtils.stripAccents(command.getMuseumName()));
        museum.setProvince(command.getProvince());
        museum.setCity(command.getCity());
        museum.setStreet(command.getStreet());
        museum.setAddressNumber(command.getAddressNumber());
        museum.setUser(user);

        return user;
    }

}
