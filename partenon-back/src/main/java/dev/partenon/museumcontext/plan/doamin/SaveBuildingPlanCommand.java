package dev.partenon.museumcontext.plan.doamin;

import dev.partenon.global.domain.abstractcomponents.command.Command;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class SaveBuildingPlanCommand implements Command {
    public String buildingPlan;
    public Long museumId;
}
