package dev.partenon.museums.contact.domain.actions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.global.domain.ports.command.Command;
import dev.partenon.museums.contact.domain.models.SocialMediaType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class SaveContactCommand implements Command {
    @NotEmpty
    private String museumId;
    @NotEmpty
    private String contact;
    @NotNull
    private SocialMediaType type;
    @JsonIgnore
    private String userId;
}
