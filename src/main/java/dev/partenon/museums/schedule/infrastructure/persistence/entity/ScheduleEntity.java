package dev.partenon.museums.schedule.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.global.domain.ports.Mapper;
import dev.partenon.museums.core.infrastructure.persistence.MuseumEntity;
import dev.partenon.museums.schedule.domain.models.ScheduleModel;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "schedule")
public final class ScheduleEntity implements Serializable {
    public static final Long serialVersionUID = 1L;

    @EmbeddedId
    private SchedulePK schedulePK;
    @Column(name = "opening", nullable = false)
    private String opening;
    @Column(name = "close", nullable = false)
    private String close;

    @JsonIgnore
    @JoinColumn(name = "museum_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private MuseumEntity museum;

    @Transient
    @JsonIgnore
    @ToString.Exclude
    public final Mapper<Void, ScheduleModel> entity = (nothing) -> new ScheduleModel(
            this.getSchedulePK().getMuseumId(),
            this.getSchedulePK().getDay(),
            this.getSchedulePK().getShift(),
            this.getOpening(),
            this.getClose()
    );

    @Transient
    @JsonIgnore
    public static final Mapper<ScheduleModel, ScheduleEntity> model = model -> {
        var schedule = new ScheduleEntity();
        schedule.setSchedulePK(new SchedulePK(model.museumId(), model.day(), model.shift()));
        schedule.setOpening(model.opening());
        schedule.setClose(model.close());
        return schedule;
    };
}
