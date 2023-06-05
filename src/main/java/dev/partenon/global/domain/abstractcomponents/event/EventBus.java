package dev.partenon.global.domain.abstractcomponents.event;

@FunctionalInterface
public interface EventBus {
    <T> T handle(Event<T> event) throws Exception;
}
