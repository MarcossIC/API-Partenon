package dev.partenon.global.domain.ports.event;

public final class EventHandlerExecutionError extends RuntimeException {
    public EventHandlerExecutionError(Throwable cause) {
        super(cause);
    }
}
