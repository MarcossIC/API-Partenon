package dev.partenon.global.domain.abstractcomponents.event;

public interface EventHandler<T, U extends Event<T>> {
    T handle(U event) throws Exception;
}
