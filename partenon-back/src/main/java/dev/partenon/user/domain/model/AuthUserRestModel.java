package dev.partenon.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthUserRestModel {

    @NotNull
    private String usernameOrEmail;
    @NotNull
    private String password;
}
