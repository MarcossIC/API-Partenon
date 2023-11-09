package dev.partenon.global.domain.ports.event;

@FunctionalInterface
public interface EventBus {
    <T> T execute(Event<T> event) throws Exception;
}
