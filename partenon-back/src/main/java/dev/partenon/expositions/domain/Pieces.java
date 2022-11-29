package dev.partenon.expositions.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pieces implements Serializable {
    public static final Long serialVersionUID = 1L;

    private Long pieceId;
    private String pieceName;
    private String description;
    private String photo;
    @JsonIgnore
    private Expositions expositions;

    public static Pieces create(SavePieceCommand command, Expositions exposition){
        var piece = new Pieces();
        piece.setPieceName(command.getPieceName());
        piece.setPhoto(command.getPhoto());
        piece.setDescription(command.getDescription());
        piece.setExpositions(exposition);

        return piece;
    }
}
