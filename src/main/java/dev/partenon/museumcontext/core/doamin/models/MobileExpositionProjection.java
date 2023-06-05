package dev.partenon.museumcontext.core.doamin.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MobileExpositionProjection {
    private String description;
    private String expositionName;
    private String category;
}
