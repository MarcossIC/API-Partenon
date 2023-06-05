package dev.partenon.museumcontext.core.doamin.models;

import dev.partenon.global.domain.abstractcomponents.query.Response;
import dev.partenon.museumcontext.core.doamin.Museum;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class MobilMuseumProjection implements Response {
    private Long museumId;
    private String name;
    private String description;
    private List<String> tours;
    private List<MobileExpositionProjection> expositions;

    public static MobilMuseumProjection create(Museum museum) {
        var projection = new MobilMuseumProjection();

        projection.setMuseumId(museum.getMuseumId());
        projection.setName(museum.getMuseumName());
        projection.setDescription(museum.getMuseumDescription() == null ? null : museum.getMuseumDescription().getDescription());

        projection.setExpositions(new LinkedList<>());
        if (!museum.getExpositions().isEmpty()) {
            museum.getExpositions().forEach(x -> {
                var exposition = new MobileExpositionProjection(x.getDescription(), x.getExpositionName(), x.getCategory());
                projection.getExpositions().add(exposition);
            });
        }

        projection.setTours(new LinkedList<>());
        if (!museum.getTours().isEmpty())
            museum.getTours().forEach(x -> projection.getTours().add(x.getTourPK().getTourName()));

        return projection;
    }
}
