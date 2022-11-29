package dev.partenon.global.infrastructure.adapters;

import dev.partenon.global.domain.abstractcomponents.event.Event;
import dev.partenon.global.domain.abstractcomponents.event.EventBus;
import dev.partenon.global.domain.abstractcomponents.event.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Primary
public class CustomEventBus implements EventBus {
    private final Map<Class, EventHandler> handlers;

    /**
     * Se encarga de buscar todos los querys
     */
    @Autowired
    public CustomEventBus(List<EventHandler> eventHandlers) {
        this.handlers = new HashMap<>();
        eventHandlers.forEach(eventHandler -> {
            Class eventClass = getEventClass(eventHandler);
            handlers.put(eventClass, eventHandler);
        });
    }

    /**
     * Busca un handler para el event, si lo encuentra ejecuta el evento
     */
    @Override
    public <T> T handle(Event<T> event) throws Exception {
        //Si no existe un Handler con este query da error
        if (!handlers.containsKey(event.getClass())) {
            throw new Exception(String.format("No handler for %s", event.getClass().getName()));
        }
        //Si no dio error entonces solo busca la implementacion y ejecuta su metodo handle
        return (T) handlers.get(event.getClass()).handle(event);
    }

    /**
     * Busca la clase de la implementacion utilizando la libreria de {@link java.lang.reflect}
     */
    public Class<?> getEventClass(EventHandler handler) {
        var methods = Arrays.stream(handler.getClass().getMethods())
                .toList().stream()
                .filter(x -> x.getName().equalsIgnoreCase("handle"))
                .filter(x -> !x.getParameterTypes()[0].getSimpleName().startsWith("Event"))
                .collect(Collectors.toList());

        return getClass(methods
                .get(0).getParameterTypes()[0].getCanonicalName());
    }

    /**
     * Una vez recupera el nombre del tipo del handler recupera la clase de esta
     */
    public Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (Exception ex) {
            return null;
        }
    }

}
