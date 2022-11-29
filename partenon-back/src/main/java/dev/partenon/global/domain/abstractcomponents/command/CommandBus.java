package dev.partenon.global.domain.abstractcomponents.command;

/**
 * CommandBus generico
 */
@FunctionalInterface
public interface CommandBus {
    void dispatch(Command command) throws CommandHandlerExecutionError;
}
