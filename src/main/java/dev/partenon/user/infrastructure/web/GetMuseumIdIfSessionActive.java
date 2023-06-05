package dev.partenon.user.infrastructure.web;

import dev.partenon.global.domain.abstractcomponents.query.QueryBus;
import dev.partenon.user.domain.InitQuery;
import dev.partenon.user.domain.model.response.MuseumIdModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * EndPoint - GET
 */
@RestController
@RequestMapping("/api/auth")
public class GetMuseumIdIfSessionActive {
    @Autowired
    private QueryBus queryBus;

    /**
     * Si la sesion del usuario esta activa, recupera el ID de su museo
     *
     * @param authorization Contiene el valor del Header de Authorization
     * @return Museum ID, HTTP Okey
     * @throws IOException
     */
    @GetMapping("/init")
    public ResponseEntity<MuseumIdModel> getMuseumIdIfSessionActive(@RequestHeader(name = AUTHORIZATION) String authorization) throws Exception {
        final var query = InitQuery.builder()
                .bearerToken(authorization)
                .build();

        MuseumIdModel response = queryBus.ask(query);
        return ResponseEntity.ok(response);
    }
}
