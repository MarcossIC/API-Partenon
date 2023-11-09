package dev.partenon.museums.schedule.domain.ports;

import dev.partenon.museums.schedule.domain.models.Days;
import dev.partenon.museums.schedule.domain.models.ScheduleModel;
import dev.partenon.museums.schedule.domain.models.Shifts;

public interface ScheduleRepository {
    //

    ScheduleModel saveContact(ScheduleModel model);

    ScheduleModel findSchedule(String museumId, Days day, Shifts shift);
}
