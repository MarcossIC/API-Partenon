package dev.partenon.user.domain.model.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public final class UserRestModel {
    @NotEmpty
    @Size(min = 2, max = 20)
    private String username;
    @NotEmpty
    private String email;
    @Size(min = 4, max = 20)
    private String password;

    @NotEmpty
    @Size(min = 2, max = 20)
    private String museumName;
    @NotEmpty
    private String province;
    @NotEmpty
    private String city;
    @NotEmpty
    private String street;
    @NotEmpty
    private String addressNumber;
}
