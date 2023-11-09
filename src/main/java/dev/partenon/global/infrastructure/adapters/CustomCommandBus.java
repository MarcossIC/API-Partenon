package dev.partenon.global.infrastructure.adapters;


import dev.partenon.global.domain.ports.command.Command;
import dev.partenon.global.domain.ports.command.CommandBus;
import dev.partenon.global.domain.ports.command.CommandHandler;
import dev.partenon.global.domain.ports.command.CommandNotRegisteredError;
import dev.partenon.global.infrastructure.ActionBusUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementacion del Command Bus
 * Un Bus, es una clase que se encarga de decidir entre una lista de Eventos
 * Cual es el que debe ejecutar
 */
@Service
@Primary
public class CustomCommandBus implements CommandBus {
    private final Map<Class, CommandHandler> handlers;
    private final ActionBusUtil<CommandHandler, Command> util;


    /**
     * Carga todos los Comandos y handler en un mapa de datos
     *
     * @param handlers Lista con todos las implementaciones de CommandHandler (Spring DI)
     */
    public CustomCommandBus(List<CommandHandler> handlers, ActionBusUtil<CommandHandler, Command> util) {
        this.util = util;

        this.handlers = new HashMap<>();
        handlers.forEach(commandHandler -> {
            Class<? extends Command> commandClass =
                    this.util.getActionClass(commandHandler, "Command");
            this.handlers.put(commandClass, commandHandler);
        });
    }

    /**
     * Lleva el comando y ejecuta la accion
     *
     * @param command Comando que se desea ejecutar
     * @throws Exception
     */
    @Override
    public void dispatch(Command command) {
        try {
            var commandHandler = searchHandler(command);
            commandHandler.handle(command);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ejecuta el comando, sin dar respuesta
     *
     * @param command Comando
     * @return Retorna la clase del Handler
     * @throws CommandNotRegisteredError
     */
    public CommandHandler searchHandler(Command command) throws CommandNotRegisteredError {
        if (!handlers.containsKey(command.getClass()))
            throw new CommandNotRegisteredError(command.getClass());

        var commandHandlerClass = handlers.get(command.getClass());
        return commandHandlerClass;
    }
}
