package dev.partenon.museums.core.domain.models.projections;

import dev.partenon.global.domain.ports.query.Response;

public interface PaginatedMuseumProjection extends Response {
    String getId();

    String getName();

    String getProvince();

    String getCity();

    String getStreet();

    String getAddressNumber();

    String getBanner();
}
