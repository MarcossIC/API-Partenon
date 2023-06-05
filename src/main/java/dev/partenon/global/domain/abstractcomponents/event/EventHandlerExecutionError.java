package dev.partenon.global.domain.abstractcomponents.event;

public final class EventHandlerExecutionError extends RuntimeException {
    public EventHandlerExecutionError(Throwable cause) {
        super(cause);
    }
}
