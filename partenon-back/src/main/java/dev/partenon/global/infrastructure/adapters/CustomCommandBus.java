package dev.partenon.global.infrastructure.adapters;


import dev.partenon.global.domain.abstractcomponents.command.Command;
import dev.partenon.global.domain.abstractcomponents.command.CommandBus;
import dev.partenon.global.domain.abstractcomponents.command.CommandHandler;
import dev.partenon.global.domain.abstractcomponents.command.CommandNotRegisteredError;
import dev.partenon.global.domain.abstractcomponents.query.QueryHandler;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Clase que recibe un comando y se encarga de buscar a que Handler le pertenece
 */
@Service
@Primary
public class CustomCommandBus implements CommandBus {
    private final Map<Class, CommandHandler> commandHandlers;

    /**
     * Se encarga de buscar todos los comandos
     */
    public CustomCommandBus(List<CommandHandler> commandHandlerImplementations) {
        this.commandHandlers = new HashMap<>();
        commandHandlerImplementations.forEach(commandHandler -> {
            Class<? extends Command> commandClass =
                    getCommandClass(commandHandler);
            this.commandHandlers.put(commandClass, commandHandler);
        });
    }

    /**
     * Busca un handler para el comando y ejecuta este si lo encuentra
     */
    @Override
    public void dispatch(Command command) {
        try {
            var handler = search(command);
            //Si no dio error entonces solo busca la implementacion y ejecuta su metodo handle
            handler.handle(command);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public CommandHandler search(Command command) throws CommandNotRegisteredError {
        var commandHandlerClass = commandHandlers.get(command.getClass());

        if (null == commandHandlerClass) {
            throw new CommandNotRegisteredError(command);
        }

        return commandHandlerClass;
    }

    /**
     * Busca la clase del comando que este relacionada con la instancia del handler pasada
     *
     * @param handler Instancia de algun command handler
     * @return command object class
     */
    public Class<? extends Command> getCommandClass(CommandHandler handler) {
        var methods = Arrays.stream(handler.getClass().getMethods()).toList()
                .stream()
                .filter(x -> x.getName().equalsIgnoreCase("handle"))
                .filter(x -> !x.getParameterTypes()[0].getSimpleName().startsWith("Command"))
                .toList();
        return (Class<? extends Command>) getClass(methods
                .get(0).getParameterTypes()[0].getCanonicalName());
    }

    /**
     * Recupera un objeto Class en base el nombre de una clase
     */
    public Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (Exception ex) {
            return null;
        }
    }
}
