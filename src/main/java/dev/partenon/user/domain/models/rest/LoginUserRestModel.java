package dev.partenon.user.domain.models.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public final class LoginUserRestModel {
    @NotNull
    private String usernameOrEmail;
    @NotNull
    private String password;
}
