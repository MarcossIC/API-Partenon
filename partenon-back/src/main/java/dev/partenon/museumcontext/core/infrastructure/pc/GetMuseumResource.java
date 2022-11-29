package dev.partenon.museumcontext.core.infrastructure.pc;

import dev.partenon.global.domain.abstractcomponents.query.QueryBus;
import dev.partenon.museumcontext.core.doamin.query.GetQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/museums")
/**Endpoint para Recuperar un museo*/
public class GetMuseumResource {
    private final QueryBus queryBus;

    @Autowired
    public GetMuseumResource(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    /**
     *
     * @param museumId ID del museo a recuperar
     * @return
     * @throws Exception
     */
    @GetMapping
    public Mono<ServerResponse> getMuseum(
            @RequestParam(value = "key") String museumId)
            throws Exception {
        var query = GetQuery.builder()
                .museumId(Long.valueOf(museumId)).build();
        var response = queryBus.ask(query);

        return ServerResponse.ok().bodyValue(response);
    }
}
