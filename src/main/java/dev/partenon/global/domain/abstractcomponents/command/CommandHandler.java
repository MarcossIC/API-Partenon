package dev.partenon.global.domain.abstractcomponents.command;

/**
 * CommandHandler Generico
 */
@FunctionalInterface
public interface CommandHandler<T extends Command> {
    void handle(T command);
}
