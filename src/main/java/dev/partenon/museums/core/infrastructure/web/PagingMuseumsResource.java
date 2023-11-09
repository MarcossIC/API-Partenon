package dev.partenon.museums.core.infrastructure.web;

import dev.partenon.global.domain.model.InfoPagination;
import dev.partenon.global.domain.model.PagedResponse;
import dev.partenon.global.domain.ports.query.QueryBus;
import dev.partenon.museums.core.domain.actions.PagingMuseumsQuery;
import dev.partenon.museums.core.domain.models.projections.PaginatedMuseumProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PagingMuseumsResource {
    private final QueryBus queryBus;

    /**
     * @param index Número de la página que se está pidiendo
     * @param size  Tamaño de la pagina
     * @param sort  Nombre del atributo sobre el que estarán ordenados los elementos
     * @param order Decide si el orden debe ser ASC(Ascendente) o DESC(Descendente)
     * @return Devuelve el objeto {@link PagedResponse} con los datos de paginacion
     */
    @GetMapping("/museums")
    public ResponseEntity<PagedResponse<PaginatedMuseumProjection>> pagingMuseum(
            @RequestParam(value = "index") int index,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "order") String order) throws Exception {

        var query = PagingMuseumsQuery.builder()
                .infoPagination(InfoPagination.build(index, size, sort, order))
                .build();

        var response = (PagedResponse<PaginatedMuseumProjection>) queryBus.ask(query);

        return ResponseEntity.ok(response);
    }
}
