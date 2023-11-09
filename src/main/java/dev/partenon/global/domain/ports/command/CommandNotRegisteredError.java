package dev.partenon.global.domain.ports.command;

public final class CommandNotRegisteredError extends Exception {
    public CommandNotRegisteredError(Class<? extends Command> command) {
        super(String.format("The command <%s> hasn't a command handler associated", command.getName()));
    }
}
