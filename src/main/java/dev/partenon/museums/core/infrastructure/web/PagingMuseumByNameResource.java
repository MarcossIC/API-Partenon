package dev.partenon.museums.core.infrastructure.web;

import dev.partenon.global.domain.model.InfoPagination;
import dev.partenon.global.domain.model.PagedResponse;
import dev.partenon.global.domain.ports.query.QueryBus;
import dev.partenon.museums.core.domain.actions.PagingByNameQuery;
import dev.partenon.museums.core.domain.models.projections.PaginatedMuseumProjection;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PagingMuseumByNameResource {
    private final QueryBus queryBus;

    /**
     * @param index Número de la página que se está pidiendo
     * @param size  Tamaño de la pagina
     * @param sort  Nombre del atributo sobre el que estarán ordenados los elementos
     * @param order Decide si el orden debe ser ASC(Ascendente) o DESC(Descendente)
     * @param name  Nombre del museo a buscar
     * @return Devuelve el objeto {@link PagedResponse} con los datos de paginacion
     * @throws Exception
     */
    @GetMapping("/museums/{name}")
    public HttpEntity<PagedResponse<PaginatedMuseumProjection>> pagingMuseumByName(
            @RequestParam(value = "index") int index,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "order") String order,
            @PathVariable("name") String name)
            throws Exception {

        var query = PagingByNameQuery.builder()
                .term(StringUtils.stripAccents(name))
                .infoPagination(InfoPagination.build(index, size, sort, order))
                .build();

        var response = (PagedResponse<PaginatedMuseumProjection>) queryBus.ask(query);
        return ResponseEntity.ok(response);
    }
}
