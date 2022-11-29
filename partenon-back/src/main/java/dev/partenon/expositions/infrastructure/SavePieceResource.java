package dev.partenon.expositions.infrastructure;

import dev.partenon.expositions.domain.SavePieceCommand;
import dev.partenon.expositions.domain.model.PieceRestModel;
import dev.partenon.global.domain.abstractcomponents.command.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/api/pieces")
public class SavePieceResource {
    private final CommandBus commandBus;

    @Autowired
    public SavePieceResource(CommandBus commandBus){
        this.commandBus = commandBus;
    }

    /**
     *
     * @param pieceRestModel Modelo rest con los datos para crear una pieza
     * @return
     * @throws Exception
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public Mono<ServerResponse>  savePiece(@RequestBody @Valid PieceRestModel pieceRestModel) throws Exception {

        var command = SavePieceCommand.builder()
                .pieceName(pieceRestModel.getPieceName())
                .description(pieceRestModel.getDescription())
                .photo(pieceRestModel.getPhoto())
                .expositionId(pieceRestModel.getExpositionId())
                .build();

        commandBus.dispatch(command);

        return ServerResponse.created(
                URI.create("http://localhost:8080/api/piece&key=".concat(String.valueOf(pieceRestModel.getExpositionId()))))
                        .build() ;
    }
}
