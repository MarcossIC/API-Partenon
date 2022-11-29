package dev.partenon.global.domain.abstractcomponents.command;

public final class CommandNotRegisteredError extends Exception {
    public CommandNotRegisteredError(Command command) {
        super(String.format("The command <%s> hasn't a command handler associated", command.toString()));
    }
}
