package dev.partenon.museumcontext.openinghours.doamin;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public final class OpeningHoursRestModel {
    @NotEmpty
    private String monday;
    @NotEmpty
    private String tuesday;
    @NotEmpty
    private String wednesday;
    @NotEmpty
    private String thursday;
    @NotEmpty
    private String friday;
    @NotEmpty
    private String saturday;
    @NotEmpty
    private String sunday;
}
