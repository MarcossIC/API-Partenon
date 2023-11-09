package dev.partenon.museums.schedule.infrastructure.persistence;

import dev.partenon.museums.schedule.domain.models.Days;
import dev.partenon.museums.schedule.domain.models.ScheduleModel;
import dev.partenon.museums.schedule.domain.models.Shifts;
import dev.partenon.museums.schedule.domain.ports.ScheduleRepository;
import dev.partenon.museums.schedule.infrastructure.persistence.entity.ScheduleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleRepositoryAdapter implements ScheduleRepository {
    private final ScheduleJpaRepository jpaRepository;

    @Override
    public ScheduleModel saveContact(ScheduleModel model) {
        this.jpaRepository.save(ScheduleEntity.model.map(model));
        return model;
    }

    @Override
    public ScheduleModel findSchedule(String museumId, Days day, Shifts shift) {
        return null;
    }
//

}
