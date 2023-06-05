package dev.partenon.global.domain.abstractcomponents.command;

import dev.partenon.global.domain.EntityNotFoundException;

/**
 * CommandHandler Generico
 */
@FunctionalInterface
public interface CommandHandler<T extends Command> {
    void handle(T command) throws EntityNotFoundException;
}
