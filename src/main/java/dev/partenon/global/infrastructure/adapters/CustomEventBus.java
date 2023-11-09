package dev.partenon.global.infrastructure.adapters;

import dev.partenon.global.domain.ports.event.Event;
import dev.partenon.global.domain.ports.event.EventBus;
import dev.partenon.global.domain.ports.event.EventHandler;
import dev.partenon.global.domain.ports.event.EventNotRegisteredError;
import dev.partenon.global.infrastructure.ActionBusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementacion del EventBus
 * Un Bus, es una clase que se encarga de decidir entre una lista de Eventos
 * Cual es el que debe ejecutar
 */
@Component
@Primary
public class CustomEventBus implements EventBus {
    //Mapa con <ImplementacionEvent, ImplementacionEventHandler>
    private final Map<Class, EventHandler> handlers;
    private final ActionBusUtil<EventHandler, Event> util;

    /**
     * Carga todos los Eventos y handler en un mapa de datos
     *
     * @param eventHandlers Lista con todos las implementaciones de EventHandler (Spring DI)
     */
    @Autowired
    public CustomEventBus(List<EventHandler> eventHandlers, ActionBusUtil<EventHandler, Event> util) {
        this.util = util;
        this.handlers = new HashMap<>();
        eventHandlers.forEach(eventHandler -> {
            Class eventClass = this.util.getActionClass(eventHandler, "Event");
            handlers.put(eventClass, eventHandler);
        });

    }

    /**
     * Ejecuta el evento enviado y da una respuesta al evento
     *
     * @param event Evento que se desea ejecutar
     * @param <T>   El tipo de Objeto que devuelve el evento
     * @return Respuesta al evento ejecutado
     * @throws Exception
     */
    @Override
    public <T> T execute(Event<T> event) throws Exception {
        if (!handlers.containsKey(event.getClass())) {
            throw new EventNotRegisteredError(event.getClass());
        }
        //Si no dio error entonces solo busca la implementacion y ejecuta su metodo handle
        return (T) handlers.get(event.getClass()).handle(event);
    }
}
