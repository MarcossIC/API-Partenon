package dev.partenon.global.infrastructure.adapters;

import dev.partenon.global.domain.abstractcomponents.command.Command;
import dev.partenon.global.domain.abstractcomponents.command.CommandHandler;
import dev.partenon.global.domain.abstractcomponents.query.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * clase que decide que implementacion de los query handler debe ejecutar
 * Implementacion de {@link QueryBus}
 */
@Service
@Primary
public class CustomQueryBus implements QueryBus {
    private final Map<Class, QueryHandler> queryHandlers;

    /**
     * Se encarga de buscar todos los querys
     */
    public CustomQueryBus(List<QueryHandler> queryHandlerImplementations) {
        this.queryHandlers = new HashMap<>();
        queryHandlerImplementations.forEach(queryHandler -> {
            Class queryClass = getQueryClass(queryHandler);
            this.queryHandlers.put(queryClass, queryHandler);
        });
    }


    /**
     * Busca un handler para la query y ejecuta este si lo encuentra
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

    public Class<?> getQueryClass(QueryHandler handler) {
        var methods = Arrays.stream(handler.getClass().getMethods())
                .toList().stream()
                .filter(x -> x.getName().equalsIgnoreCase("handle"))
                .filter(x -> !x.getParameterTypes()[0].getSimpleName().startsWith("Query"))
                .toList();
        return getClass(methods
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
