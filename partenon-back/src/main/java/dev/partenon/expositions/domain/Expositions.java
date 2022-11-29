package dev.partenon.expositions.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.museumcontext.core.doamin.Museum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Expositions {
    private Long expositionId;
    private String expositionName;
    private String description;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;
    @JsonIgnore
    private Museum museum;

    public static Expositions create(SaveExpositionCommand command, Museum museum) {
        var exposition = new Expositions();
        exposition.setExpositionName(command.getName());
        exposition.setDescription(command.getDescription());
        exposition.setMuseum(museum);
        exposition.setCategory(command.getCategory());
        exposition.setStartDate(LocalDate.now());
        exposition.setEndDate(LocalDate.parse(command.getEndDate()));
        return exposition;
    }

    @Override
    public String toString() {
        return "Expositions{" + "expositionId=" + expositionId + ", expositionName='" + expositionName + '\'' + ", description='" + description + '\'' + ", category='" + category + '\'' + '}';
    }
}
