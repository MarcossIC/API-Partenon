package dev.partenon.museums.banner.domain.actions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.global.domain.ports.command.Command;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class SaveBannerCommand implements Command {
    @NotEmpty
    private String museumId;
    @NotEmpty
    private String museumBanner;
    @JsonIgnore
    private String userId;
}
