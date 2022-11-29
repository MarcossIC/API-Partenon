package dev.partenon.expositions.application.handlers;

import dev.partenon.expositions.application.ExpositionRepository;
import dev.partenon.expositions.domain.Expositions;
import dev.partenon.expositions.domain.PagingExpositionsQuery;
import dev.partenon.global.domain.abstractcomponents.query.QueryHandler;
import dev.partenon.global.domain.model.Page;
import dev.partenon.global.domain.model.PagedResponse;
import dev.partenon.museumcontext.core.doamin.Museum;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**Maneja la Query para PagingExpositionResource*/
@Log4j2
@Service
@AllArgsConstructor
public class PagingExpositionsQueryHandler implements QueryHandler<PagingExpositionsQuery, PagedResponse<Expositions>> {

    @Autowired
    private ExpositionRepository repository;

    @Override
    public PagedResponse<Expositions> handle(PagingExpositionsQuery query) {
        validatePage(query.getPage());
        var expositions =repository.findByMuseum(new Museum(query.getMuseumId()), createPageable(query.getPage()))
                            .subscribeOn(Schedulers.boundedElastic())
                            .delayElements(Duration.ofMillis(1))
                            .switchIfEmpty(Flux.empty());
        var content = expositions.collectSortedList();
        log.info("------------------------LOG WITH COLLECT SORTED LIST----------------------------");
        expositions.collectSortedList().block().forEach(x-> log.info("EXPOSITIONS IS: {}", x));
        log.info("--------------------------------------------------------------------------");
        log.info("----------------------LOG WITH LIST------------------------------------");
        expositions.collectList().block().forEach(x-> log.info("EXPOSITIONS IS: {}", x));
        log.info("--------------------------------------------------------------------------");
        log.info("----------------------LOG WITH STREAMS------------------------------------");
        expositions.toStream().toList().forEach(x-> log.info("EXPOSITIONS IS: {}", x));
        log.info("--------------------------------------------------------------------------");
        return new PagedResponse(content.block(), query.getPage().getIndex(), query.getPage().getSize(),
                content.block().size(), content.block().size() <= query.getPage().getSize());
    }

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
