package dev.partenon.global.infrastructure.adapters;

import dev.partenon.global.domain.ports.command.CommandNotRegisteredError;
import dev.partenon.global.domain.ports.query.*;
import dev.partenon.global.infrastructure.ActionBusUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * clase que decide que implementacion de los query handler debe ejecutar
 * Implementacion de {@link QueryBus}
 */
@Service
@Primary
public class CustomQueryBus implements QueryBus {
    private final Map<Class, QueryHandler> queryHandlers;
    private final ActionBusUtil<QueryHandler, Query> util;


    /**
     * Carga todos los Comandos y handler en un mapa de datos
     *
     * @param query Lista con todos las implementaciones de CommandHandler (Spring DI)
     */
    public CustomQueryBus(List<QueryHandler> query, ActionBusUtil<QueryHandler, Query> util) {
        this.util = util;

        this.queryHandlers = new HashMap<>();
        query.forEach(queryHandler -> {
            Class queryClass = this.util.getActionClass(queryHandler, "Query");
            this.queryHandlers.put(queryClass, queryHandler);
        });
    }


    /**
     * Hace la consulta y devuelve el valor consultado
     *
     * @param query Consulta con la informacion para recuperar los datos
     * @return Devuelva una respuesta a la consulta
     * @throws CommandNotRegisteredError
     */
    @Override
    public Response ask(Query query) throws Exception {
        try {
            var handler = this.search(query);
            return handler.handle(query);
        } catch (Throwable error) {
            throw new QueryHandlerExecutionError(error);
        }
    }

    private QueryHandler search(Query query) throws QueryNotRegisteredError {
        var queryHandlerClass = queryHandlers.get(query.getClass());

        if (null == queryHandlerClass) {
            throw new QueryNotRegisteredError(query.getClass());
        }
        return queryHandlerClass;
    }
}
