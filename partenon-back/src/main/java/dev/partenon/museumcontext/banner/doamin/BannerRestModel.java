package dev.partenon.museumcontext.banner.doamin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BannerRestModel {
    @JsonProperty("banner")
    @NotNull
    private String banner;
}
