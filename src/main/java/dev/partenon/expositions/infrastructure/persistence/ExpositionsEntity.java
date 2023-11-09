package dev.partenon.expositions.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.expositions.domain.model.ExpositionModel;
import dev.partenon.global.domain.ports.Mapper;
import dev.partenon.museums.core.infrastructure.persistence.MuseumEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "expositions")
@Data
public final class ExpositionsEntity implements Serializable {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "name", nullable = false)
    private String photo;
    @Column(name = "name", nullable = false)
    private String description;
    @Column(name = "name", nullable = false)
    private LocalDate endDate;
    @Column(name = "name", nullable = false)
    private LocalDate createDate;

    @JsonIgnore
    @JoinColumn(name = "museum_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private MuseumEntity museum;

    @Transient
    @JsonIgnore
    @ToString.Exclude
    public final Mapper<Void, ExpositionModel> entity = (nothing) -> new ExpositionModel(
            this.getId(),
            this.getMuseum().getId(),
            this.getName(),
            this.getPhoto(),
            this.getDescription(),
            this.getEndDate(),
            this.getCreateDate()
    );

    @Transient
    @JsonIgnore
    public static final Mapper<ExpositionModel, ExpositionsEntity> model = model -> {
        var exposition = new ExpositionsEntity();
        exposition.setId(model.id());
        exposition.setMuseum(new MuseumEntity(model.museumId()));

        exposition.setName(model.name());
        exposition.setDescription(model.description());
        exposition.setPhoto(model.photo());
        exposition.setCreateDate(model.createDate());
        exposition.setEndDate(model.endDate());
        return exposition;
    };
}
