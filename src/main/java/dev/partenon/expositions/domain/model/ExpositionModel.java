package dev.partenon.expositions.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public record ExpositionModel(
        String id,
        String museumId,
        String name,
        String photo,
        String description,
        LocalDate endDate,
        LocalDate createDate) {

    public ExpositionModel(String museumId, String name, String photo, String description, LocalDate endDate) {
        this(UUID.randomUUID().toString(), museumId, name, photo, description, endDate, LocalDate.now());
    }

}
