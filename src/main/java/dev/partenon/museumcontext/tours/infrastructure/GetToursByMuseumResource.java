package dev.partenon.museumcontext.tours.infrastructure;

import dev.partenon.global.domain.abstractcomponents.query.QueryBus;
import dev.partenon.museumcontext.tours.domain.GetTourByMuseumQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Endpoint para recupera los Tours de un museo
 */
@RestController
@RequestMapping("/api/museums")
public class GetToursByMuseumResource {
    private final QueryBus queryBus;

    @Autowired
    public GetToursByMuseumResource(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    /**
     * @param museumId ID del museo
     * @return
     * @throws Exception
     */
    @GetMapping("/tours")
    public Mono<ServerResponse> getTours(@RequestParam("key") String museumId)
            throws Exception {
        var query = GetTourByMuseumQuery.builder()
                .museumId(Long.parseLong(museumId))
                .build();

        var response = queryBus.ask(query);

        return ServerResponse.ok().bodyValue(response);
    }
}
