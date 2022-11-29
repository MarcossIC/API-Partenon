package dev.partenon.expositions.application;

import dev.partenon.expositions.domain.Pieces;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface PieceRepository extends ReactiveSortingRepository<Pieces, Long> {
}
