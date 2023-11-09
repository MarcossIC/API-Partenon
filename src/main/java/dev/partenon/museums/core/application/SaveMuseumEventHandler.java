package dev.partenon.museums.core.application;

import dev.partenon.global.domain.model.MuseumIdModel;
import dev.partenon.global.domain.ports.event.EventHandler;
import dev.partenon.museums.core.domain.actions.MuseumSaveEvent;
import dev.partenon.museums.core.domain.models.Museum;
import dev.partenon.museums.core.domain.ports.MuseumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SaveMuseumEventHandler implements EventHandler<MuseumIdModel, MuseumSaveEvent> {
    private final MuseumRepository repository;
    private final Function<MuseumSaveEvent, Museum> eventToModel = event -> new Museum(event.getUserId(), event.getName(), event.getProvince(), event.getCity(), event.getStreet(), event.getAddressNumber(), event.getDescription());

    @Override
    public MuseumIdModel handle(MuseumSaveEvent event) throws Exception {
        if (event.getUserId() == null) throw new IllegalArgumentException("User ID cannot be null");

        var museum = repository.saveMuseum(this.eventToModel.apply(event));
        return MuseumIdModel.builder().museumId(museum.id()).build();
    }
}
