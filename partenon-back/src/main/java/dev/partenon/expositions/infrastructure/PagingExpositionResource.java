package dev.partenon.expositions.infrastructure;

import dev.partenon.expositions.domain.PagingExpositionsQuery;
import dev.partenon.global.domain.abstractcomponents.query.QueryBus;
import dev.partenon.global.domain.model.Page;
import dev.partenon.global.domain.model.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**Endpoint para paginar las expociciones de un museo*/
@RestController
@RequestMapping("/api/expositions")
public class PagingExpositionResource {
    private final QueryBus queryBus;

    @Autowired
    public PagingExpositionResource(QueryBus queryBus){
        this.queryBus = queryBus;
    }

    /**
     *
     * @param index    Numero de pagina
     * @param size     Tamaño de pagina
     * @param sort     Propiedad en base a la que se ordena
     * @param order    Orden asc || desc
     * @param museumId ID del museo que tiene las expociciones
     * @return  Objeto {@link PagedResponse} clase con datos necesarios para controlar la paginacion en el front
     * @throws Exception
     */
    @GetMapping("/by")
    public Mono<ServerResponse> pagingExpositions(
            @RequestParam(value = "index") int index,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "order") String order,
            @RequestParam("key") String museumId) throws Exception {
        var query = PagingExpositionsQuery.Builder.getInstance()
                .museumId(museumId)
                .page(Page.starter(index, size, sort, order))
                .build();

        var response = queryBus.ask(query);

        return ServerResponse.ok().bodyValue(response);
    }
}
