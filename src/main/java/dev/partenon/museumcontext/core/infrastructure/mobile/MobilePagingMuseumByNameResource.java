package dev.partenon.museumcontext.core.infrastructure.mobile;

import dev.partenon.global.domain.abstractcomponents.query.QueryBus;
import dev.partenon.global.domain.model.Page;
import dev.partenon.global.domain.model.PagedResponse;
import dev.partenon.museumcontext.core.doamin.query.MobilePagingByNameQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/mobil/museums")
/**Endpoint para app movil, paginar museo por nombre*/
public class MobilePagingMuseumByNameResource {
    private final QueryBus queryBus;

    @Autowired
    public MobilePagingMuseumByNameResource(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    /**
     * @param index Número de la página que se está pidiendo
     * @param size  Tamaño de la pagina
     * @param sort  Nombre del atributo sobre el que estarán ordenados los elementos
     * @param order Decide si el orden debe ser ASC(Ascendente) o DESC(Descendente)
     * @param name  Nombre del museo a buscar
     * @return Devuelve el objeto {@link PagedResponse} con los datos de paginacion
     * @throws Exception
     */
    @GetMapping("/{name}")
    public Mono<ServerResponse> getMuseumByNameMobile(
            @RequestParam(value = "index") int index,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "order") String order,
            @PathVariable("name") String name)
            throws Exception {
        var query = MobilePagingByNameQuery.builder()
                .charactersNames(StringUtils.stripAccents(name))
                .page(Page.starter(index, size, sort, order))
                .build();
        var response = queryBus.ask(query);

        return ServerResponse.ok().bodyValue(response);
    }
}
