package dev.partenon.museumcontext.banner.doamin;

import dev.partenon.global.domain.abstractcomponents.command.Command;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveBannerCommand implements Command {
    private String museumBanner;
    private Long museumId;
}
