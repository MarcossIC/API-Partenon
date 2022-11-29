package dev.partenon.museumcontext.openinghours.doamin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.museumcontext.core.doamin.Museum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
/**Entidad Horario de Apertura*/
public final class OpeningHours implements Serializable {
    public static final Long serialVersionUID = 1L;
    @JsonIgnore
    private Long openingHoursId;

    @JsonIgnore
    private Museum museum;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;

    public static OpeningHours create(UpdateOpeningHoursCommand opening, OpeningHours currentOpening){
        if(StringUtils.hasText(opening.getMonday()))
            currentOpening.setMonday(opening.getMonday());
        if(StringUtils.hasText(opening.getTuesday()))
            currentOpening.setTuesday(opening.getTuesday());
        if(StringUtils.hasText(opening.getWednesday()))
            currentOpening.setWednesday(opening.getWednesday());
        if(StringUtils.hasText(opening.getThursday()))
            currentOpening.setThursday(opening.getThursday());
        if(StringUtils.hasText(opening.getFriday()))
            currentOpening.setFriday(opening.getFriday());
        if(StringUtils.hasText(opening.getSaturday()))
            currentOpening.setSaturday(opening.getSaturday());
        if(StringUtils.hasText(opening.getSunday()))
            currentOpening.setSunday(opening.getSunday());
        return currentOpening;
    }
    public static OpeningHours create(SaveOpeningHoursCommand command, Museum museum){
        var opening = new OpeningHours();

        opening.setMonday(command.getMonday());
        opening.setTuesday(command.getTuesday());
        opening.setWednesday(command.getWednesday());
        opening.setThursday(command.getThursday());
        opening.setFriday(command.getFriday());
        opening.setSaturday(command.getSaturday());
        opening.setSunday(command.getSunday());
        opening.setMuseum(museum);

        return opening;
    }

    @Override
    public String toString() {
        return "OpeningHours{" +
                "openingHoursId=" + openingHoursId +
                ", monday='" + monday + '\'' +
                ", tuesday='" + tuesday + '\'' +
                ", wednesday='" + wednesday + '\'' +
                ", thursday='" + thursday + '\'' +
                ", friday='" + friday + '\'' +
                ", saturday='" + saturday + '\'' +
                ", sunday='" + sunday + '\'' +
                '}';
    }
}
