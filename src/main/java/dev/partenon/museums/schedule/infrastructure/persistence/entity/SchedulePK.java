package dev.partenon.museums.schedule.infrastructure.persistence.entity;

import dev.partenon.museums.schedule.domain.models.Days;
import dev.partenon.museums.schedule.domain.models.Shifts;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@Embeddable
public class SchedulePK implements Serializable {
    public static final Long serialVersionUID = 1L;

    @Column(name = "museum_id", nullable = false)
    private String museumId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "day", columnDefinition = "ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY')", nullable = false)
    private Days day;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "shift", columnDefinition = "ENUM('MORNING', 'AFTERNOON', 'CONTINUOUS')", nullable = false)
    private Shifts shift;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchedulePK that)) return false;
        return Objects.equals(getMuseumId(), that.getMuseumId()) && getDay() == that.getDay() && getShift() == that.getShift();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMuseumId(), getDay(), getShift());
    }
}
