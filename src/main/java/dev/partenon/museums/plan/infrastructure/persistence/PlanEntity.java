package dev.partenon.museums.plan.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.global.domain.ports.Mapper;
import dev.partenon.museums.core.infrastructure.persistence.MuseumEntity;
import dev.partenon.museums.plan.domain.models.PlanModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "museum_plan")
@Data
/**Entidad Plano*/
public class PlanEntity implements Serializable {
    public static final Long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    private String museumId;
    @Column(name = "building_plan", nullable = false)
    private String buildingPlan;

    @OneToOne
    @JoinColumn(name = "museum_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    private MuseumEntity museum;

    @Transient
    @JsonIgnore
    @ToString.Exclude
    public final Mapper<Void, PlanModel> entity = (nothing) -> new PlanModel(this.getMuseumId(), this.getBuildingPlan());

    @Transient
    @JsonIgnore
    public static final Mapper<PlanModel, PlanEntity> model = model -> {
        var museumBanner = new PlanEntity();
        museumBanner.setBuildingPlan(model.plan());
        museumBanner.setMuseumId(model.museumId());
        return museumBanner;
    };

    @Override
    public String toString() {
        return "MuseumPlan{" +
                ", buildingPlan='" + buildingPlan + '\'' +
                '}';
    }
}
