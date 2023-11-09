package dev.partenon.museums.plan.domain.actions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.global.domain.ports.command.Command;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Data
@Builder
public class SaveBuildingPlanCommand implements Command {
    @NotEmpty
    public final String museumId;
    @NotEmpty
    public final String buildingPlan;
    @JsonIgnore
    public String userId;
}
