package dev.partenon.global.domain.ports.event;

public interface EventHandler<T, U extends Event<T>> {
    T handle(U event) throws Exception;
}
