package dev.partenon.expositions.infrastructure.web;

import dev.partenon.expositions.domain.actions.PagingExpositionsQuery;
import dev.partenon.global.domain.model.InfoPagination;
import dev.partenon.global.domain.model.PagedResponse;
import dev.partenon.global.domain.ports.query.QueryBus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoint para paginar las expociciones de un museo
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PagingExpositionResource {
    private final QueryBus queryBus;

    /**
     * @param index    Numero de pagina
     * @param size     Tamaño de pagina
     * @param sort     Propiedad en base a la que se ordena
     * @param order    Orden asc || desc
     * @param museumId ID del museo que tiene las expociciones
     * @return Objeto {@link PagedResponse} clase con datos necesarios para controlar la paginacion en el front
     * @throws Exception
     */
    @GetMapping("/expositions/museum/{id}")
    public HttpEntity<PagedResponse<?>> pagingExpositions(
            @RequestParam(value = "index") int index,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "order") String order,
            @PathVariable("id") String museumId) throws Exception {

        var query = PagingExpositionsQuery.builder()
                .museumId(museumId)
                .infoPagination(InfoPagination.build(index, size, sort, order))
                .build();

        var response = (PagedResponse<?>) queryBus.ask(query);

        return ResponseEntity.ok(response);
    }
}
