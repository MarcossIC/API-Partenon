package dev.partenon.global.domain.ports.event;

public final class EventNotRegisteredError extends Exception {
    public EventNotRegisteredError(Class<? extends Event> event) {
        super(String.format("The event <%s> hasn't a event handler associated", event.getName()));
    }
}
