package dev.partenon.museumcontext.description.doamin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.museumcontext.core.doamin.Museum;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/** Entidad descripcion */
public final class MuseumDescription implements Serializable {
    public static final Long serialVersionUID = 1L;

    @JsonIgnore
    private Long descriptionId;
    private String description;

    @JsonIgnore
    private Museum museum;

    public static MuseumDescription create(SaveDescriptionCommand command, Museum museum){
        var museumDescription = new MuseumDescription();
        museumDescription.setDescription(command.getDescription());
        museumDescription.setMuseum(museum);
        return museumDescription;
    }

    @Override
    public String toString() {
        return "MuseumDescription{" +
                "museumDescriptionId=" + descriptionId +
                ", museumDescription='" + description + '\'' +
                '}';
    }
}
