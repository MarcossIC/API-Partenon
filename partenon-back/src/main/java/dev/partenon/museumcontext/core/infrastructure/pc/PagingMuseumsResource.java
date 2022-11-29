package dev.partenon.museumcontext.core.infrastructure.pc;

import dev.partenon.global.domain.abstractcomponents.query.QueryBus;
import dev.partenon.global.domain.model.Page;
import dev.partenon.global.domain.model.PagedResponse;
import dev.partenon.museumcontext.core.doamin.query.PagingMuseumsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/museums")
/**Endpoint para paginar museo*/
public class PagingMuseumsResource {
    private final QueryBus queryBus;

    @Autowired
    public PagingMuseumsResource(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    /**
     *
     * @param index    Número de la página que se está pidiendo
     * @param size     Tamaño de la pagina
     * @param sort     Nombre del atributo sobre el que estarán ordenados los elementos
     * @param order    Decide si el orden debe ser ASC(Ascendente) o DESC(Descendente)
     * @return Devuelve el objeto {@link PagedResponse} con los datos de paginacion
     */
    @GetMapping("/all")
    public Mono<ServerResponse> pagingMuseum(
            @RequestParam(value = "index") int index,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "order") String order) throws Exception {

        var query = PagingMuseumsQuery.Builder.getInstance()
                .page(Page.starter(index, size, sort, order))
                .build();
        var response = queryBus.ask(query);

        return ServerResponse.ok().bodyValue(response);
    }
}
