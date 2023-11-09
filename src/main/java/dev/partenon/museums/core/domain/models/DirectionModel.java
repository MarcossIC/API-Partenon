package dev.partenon.museums.core.domain.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DirectionModel {
    private final String province;
    private final String city;
    private final String street;
    private final String addressNumber;
}
