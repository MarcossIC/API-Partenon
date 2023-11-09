package dev.partenon.global.domain.ports.command;

/**
 * CommandHandler Generico
 */
@FunctionalInterface
public interface CommandHandler<T extends Command> {
    void handle(T command) throws Exception;
}
