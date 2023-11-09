package dev.partenon.museums.core.infrastructure.persistence;

import dev.partenon.global.domain.model.InfoPagination;
import dev.partenon.global.domain.model.PagedResponse;
import dev.partenon.global.domain.ports.Mapper;
import dev.partenon.museums.core.domain.models.Museum;
import dev.partenon.museums.core.domain.models.projections.MuseumSummaryProjection;
import dev.partenon.museums.core.domain.models.projections.PaginatedMuseumProjection;
import dev.partenon.museums.core.domain.ports.MuseumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MuseumRepositoryAdapter implements MuseumRepository {
    private final MuseumJpaRepository jpaRepository;

    private final Mapper<InfoPagination, Pageable> pageToPageable = page -> PageRequest.of(
            page.getIndex(),
            page.getSize(),
            page.getOrder().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
            page.getSort()
    );

    @Transactional
    @Override
    public Museum saveMuseum(Museum model) {
        var entity = MuseumEntity.model.map(model);
        Optional.ofNullable( model )
                .map(MuseumEntity.model::map)
                .ifPresent(this.jpaRepository::save);
        return Optional.ofNullable( model ).orElseThrow(()->new IllegalArgumentException("Museum cannot be null in save museum repository"));
    }

    @Override
    public Optional<Museum> searchMuseumByUserIdAndName(String userId, String name) {
        return this.jpaRepository.findByUserEntityUserIdAndName(userId, name).map(museum -> museum.entity.map(null));
    }

    @Override
    public Optional<MuseumSummaryProjection> getMuseumSummary(String museumId) {
        return this.jpaRepository.searchSummaryById(museumId);
    }

    @Override
    public PagedResponse<PaginatedMuseumProjection> pageMuseumByName(InfoPagination infoPagination, String term) {
        var pageable = this.pageToPageable.map(infoPagination);
        var museumPageable = this.jpaRepository.searchByName(term, pageable);

        return new PagedResponse<PaginatedMuseumProjection>(
                museumPageable.getContent(),
                museumPageable.getNumber(),
                museumPageable.getNumberOfElements(),
                museumPageable.getTotalElements(),
                museumPageable.getTotalPages(),
                museumPageable.isLast()
        );
    }

    @Override
    public Boolean museumExistsTo(String museumId, String userId) {
        return !this.jpaRepository.findByMuseumIdAndUserId(museumId, userId).isEmpty();
    }
}
