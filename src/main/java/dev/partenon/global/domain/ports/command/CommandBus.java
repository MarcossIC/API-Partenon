package dev.partenon.global.domain.ports.command;

/**
 * CommandBus generico
 */
@FunctionalInterface
public interface CommandBus {
    void dispatch(Command command) throws CommandHandlerExecutionError;
}
