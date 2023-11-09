package dev.partenon.museums.core.domain.models.projections;

public interface Schedule {
    String getDay();

    String getShift();

    String getOpening();

    String getClose();
}
