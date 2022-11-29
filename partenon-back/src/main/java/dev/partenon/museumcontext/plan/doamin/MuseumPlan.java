package dev.partenon.museumcontext.plan.doamin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.museumcontext.core.doamin.Museum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
/**Entidad Plano*/
public class MuseumPlan implements Serializable {
    public static final Long serialVersionUID = 1L;

    @JsonIgnore
    private Long planId;

    @JsonIgnore
    private Museum museum;
    private String buildingPlan;

    public static MuseumPlan create(SaveBuildingPlanCommand command, Museum museum){
        var museumPlan = new MuseumPlan();
        museumPlan.setBuildingPlan(command.getBuildingPlan());
        museumPlan.setMuseum(museum);
        return museumPlan;
    }

    @Override
    public String toString() {
        return "MuseumPlan{" +
                "museumPlanId=" + planId +
                ", buildingPlan='" + buildingPlan + '\'' +
                '}';
    }
}
