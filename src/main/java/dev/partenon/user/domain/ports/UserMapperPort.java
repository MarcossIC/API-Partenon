package dev.partenon.user.domain.ports;

import dev.partenon.user.domain.RegisterCommand;
import dev.partenon.user.domain.User;
import dev.partenon.user.domain.model.response.KeysApiModel;
import dev.partenon.user.domain.model.response.MuseumIdModel;

public interface UserMapperPort {
    KeysApiModel mapKeysAPI(User user);

    MuseumIdModel mapMuseumIdModel(Long value);

    User mapUser(RegisterCommand command);
}
