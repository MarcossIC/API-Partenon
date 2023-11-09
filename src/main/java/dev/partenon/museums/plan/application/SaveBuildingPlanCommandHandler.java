package dev.partenon.museums.plan.application;

import dev.partenon.global.domain.exceptions.ContentNotFoundException;
import dev.partenon.global.domain.ports.command.CommandHandler;
import dev.partenon.museums.core.domain.ports.MuseumRepository;
import dev.partenon.museums.plan.domain.actions.SaveBuildingPlanCommand;
import dev.partenon.museums.plan.domain.models.PlanModel;
import dev.partenon.museums.plan.domain.ports.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveBuildingPlanCommandHandler implements CommandHandler<SaveBuildingPlanCommand> {
    private final PlanRepository repository;
    private final MuseumRepository museumRepository;

    @Override
    public void handle(SaveBuildingPlanCommand command) {
        if (!this.museumRepository.museumExistsTo(command.getMuseumId(), command.getUserId()))
            throw new ContentNotFoundException("Tu identificacion no tiene permitido o no pertenece al museo.", "SaveContactCommandHandler");

        var plan = this.repository.findPlan(command.getMuseumId());

        if (!command.getBuildingPlan().equals(plan.plan()))
            this.repository.savePlan(new PlanModel(command.getMuseumId(), command.getBuildingPlan()));
    }
}
