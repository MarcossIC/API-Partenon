package dev.partenon.museums.schedule.domain.models;

public record ScheduleModel(String museumId, Days day, Shifts shift, String opening, String close) {
    public ScheduleModel(String opening, String close) {
        this(null, null, null, opening, close);
    }
}
