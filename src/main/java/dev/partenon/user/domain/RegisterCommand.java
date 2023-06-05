package dev.partenon.user.domain;

import dev.partenon.global.domain.abstractcomponents.command.Command;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public final class RegisterCommand implements Command {
    @NotEmpty
    private final String username;
    @NotEmpty
    private final String email;
    @NotEmpty
    private final String password;
    @NotEmpty
    private final String museumName;
    @NotEmpty
    private final String province;
    @NotEmpty
    private final String city;
    @NotEmpty
    private final String street;
    @NotEmpty
    private final String addressNumber;

}
