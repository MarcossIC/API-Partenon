package dev.partenon.museumcontext.core.application.handlers.pc;

import dev.partenon.global.domain.abstractcomponents.query.QueryHandler;
import dev.partenon.global.domain.model.Page;
import dev.partenon.global.domain.model.PagedResponse;
import dev.partenon.museumcontext.core.infrastructure.persistence.MuseumRepository;
import dev.partenon.museumcontext.core.doamin.models.MuseumProjection;
import dev.partenon.museumcontext.core.doamin.query.PagingMuseumsQuery;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Service
@AllArgsConstructor
/**Maneja la Query de PagingMuseumResource*/
public class PagingMuseumsQueryHandler implements QueryHandler<PagingMuseumsQuery, PagedResponse<MuseumProjection>> {
    @Autowired
    private MuseumRepository repository;

    @Override
    public PagedResponse<MuseumProjection> handle(PagingMuseumsQuery query) throws Exception {
        //Validacion
        validatePage(query.getPage());

        var pages = repository.findAll(createPageable(query.getPage()).getSort())
                .subscribeOn(Schedulers.boundedElastic())
                .delayElements(Duration.ofMillis(1))
                .switchIfEmpty(Flux.empty())
                .skip(query.getPage().getIndex() <= 1 ? 0 : ((query.getPage().getIndex() - 1) * (query.getPage().getSize()))).log()
                .limitRate(query.getPage().getSize())
                .mapNotNull(x -> new MuseumProjection(
                        x.getMuseumId(),
                        x.getMuseumName(),
                        x.getMuseumBanner() != null ? x.getMuseumBanner().getBanner() : null,
                        x.getMuseumDescription() != null ? x.getMuseumDescription().getDescription() : null))
                .checkpoint();

        var content = pages.collectSortedList();
        return new PagedResponse(content.block(), query.getPage().getIndex(), query.getPage().getSize(),
                content.block().size(), content.block().size() <= query.getPage().getSize());
    }

    /**
     * Crea el objeto {@link Pageable} necesario para la paginación
     *
     * @param page Contiene los parámetros  necesarios para crear el objeto
     * @return
     */
    private Pageable createPageable(Page page) {
        if (page.getOrder().equals("asc")) {
            return PageRequest.of(page.getIndex(), page.getSize(), Sort.Direction.ASC, page.getSort());
        }
        return PageRequest.of(page.getIndex(), page.getSize(), Sort.Direction.DESC, page.getSort());
    }

    /**
     * Comprueba que lo datos de paginacion tengan sentido
     *
     * @param page Contiene los elementos a validar
     */
    private void validatePage(Page page) {
        if (page.getIndex() < 0) {
            throw new RuntimeException("Page number cannot be less than zero.");
        }

        if (page.getSize() < 0) {
            throw new RuntimeException("Size number cannot be less than zero.");
        }

        if (page.getSize() > 12) {
            throw new RuntimeException("Page size must not be greater than " + 12);
        }
        if (!page.getOrder().equals("asc") && !page.getOrder().equals("desc")) {
            throw new RuntimeException("Order not found");
        }
    }
}
