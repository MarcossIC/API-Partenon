package dev.partenon.museumcontext.plan.application;

import dev.partenon.global.domain.abstractcomponents.command.CommandHandler;
import dev.partenon.museumcontext.core.application.MuseumRepository;
import dev.partenon.museumcontext.plan.doamin.MuseumPlan;
import dev.partenon.museumcontext.plan.doamin.SaveBuildingPlanCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static dev.partenon.global.infrastructure.UtilReactor.catchingQueryTimeoutException;

@Service
@AllArgsConstructor
/**Maneja el comando de SaveBuildingPlanResource*/
public class SaveBuildingPlanCommandHandler implements CommandHandler<SaveBuildingPlanCommand> {
    @Autowired
    private MuseumRepository museumRepository;
    @Autowired
    private PlanRepository planRepository;

    @Override
    public void handle(SaveBuildingPlanCommand command) {
        Mono.just(command)
                .doOnNext(commandValues -> museumRepository.findByMuseumId(commandValues.getMuseumId())
                        .subscribeOn(Schedulers.immediate())
                        .switchIfEmpty(Mono.error(new RuntimeException("ID No registrado")))
                        .map(museum -> MuseumPlan.create(commandValues, museum))
                        .flatMap(planRepository::save)
                        .retryWhen(catchingQueryTimeoutException).log()
                ).checkpoint();

    }
}
