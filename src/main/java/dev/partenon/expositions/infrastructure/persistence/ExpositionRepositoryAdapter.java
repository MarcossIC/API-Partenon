package dev.partenon.expositions.infrastructure.persistence;

import dev.partenon.expositions.domain.model.ExpositionModel;
import dev.partenon.expositions.domain.ports.ExpositionRepository;
import dev.partenon.global.domain.model.InfoPagination;
import dev.partenon.global.domain.model.PagedResponse;
import dev.partenon.global.domain.ports.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExpositionRepositoryAdapter implements ExpositionRepository {
    private final ExpositionJpaRepository jpaRepository;

    private final Mapper<InfoPagination, Pageable> pageToPageable = page -> PageRequest.of(
            page.getIndex(),
            page.getSize(),
            page.getOrder().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
            page.getSort()
    );

    @Override
    public ExpositionModel saveExposition(ExpositionModel model) {
        Optional.ofNullable( model )
                .map(ExpositionsEntity.model::map)
                .ifPresent(this.jpaRepository::save);

        return Optional.ofNullable( model ).orElseThrow(() -> new IllegalArgumentException("Exposition in exposition save repository is null"));
    }

    @Override
    public List<ExpositionModel> expositionByMuseumId(String museumId) {
        return null;
    }

    @Override
    public PagedResponse<ExpositionModel> pagingExpositionsByMuseumId(String museumId, InfoPagination page) {

        var pageable = this.pageToPageable.map(page);
        var pageableExposition = this.jpaRepository.findByMuseumId(museumId, pageable);

        var contentModel = pageableExposition.getContent().stream().map(exposition -> exposition.entity.map(null)).toList();

        return new PagedResponse<>(
                contentModel,
                pageableExposition.getNumber(),
                pageableExposition.getNumberOfElements(),
                pageableExposition.getTotalElements(),
                pageableExposition.getTotalPages(),
                pageableExposition.isLast()
        );
    }


}
