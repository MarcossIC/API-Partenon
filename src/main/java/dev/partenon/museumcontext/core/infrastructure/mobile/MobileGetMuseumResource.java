package dev.partenon.museumcontext.core.infrastructure.mobile;

import dev.partenon.global.domain.abstractcomponents.query.QueryBus;
import dev.partenon.museumcontext.core.doamin.query.MobileGetQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/mobil/museums")
/**Endpoint para Mobil, Recuperar un museo*/
public class MobileGetMuseumResource {
    private final QueryBus queryBus;

    @Autowired
    public MobileGetMuseumResource(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    /**
     * @param museumId ID del museo a recuperar
     * @return
     * @throws Exception
     */
    @GetMapping
    public Mono<ServerResponse> getMuseumMobile(
            @RequestParam(value = "key") String museumId)
            throws Exception {
        var query = MobileGetQuery.builder()
                .museumId(Long.parseLong(museumId))
                .build();

        var response = queryBus.ask(query);

        return ServerResponse.ok().bodyValue(response);
    }
}
