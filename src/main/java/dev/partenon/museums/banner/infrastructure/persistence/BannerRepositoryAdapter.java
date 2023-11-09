package dev.partenon.museums.banner.infrastructure.persistence;

import dev.partenon.museums.banner.domain.models.BannerModel;
import dev.partenon.museums.banner.domain.ports.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BannerRepositoryAdapter implements BannerRepository {
    private final BannerJpaRepository jpaRepository;

    @Override
    public BannerModel saveBanner(BannerModel model) {
        this.jpaRepository.save(BannerEntity.model.map(model));
        return model;
    }

    @Override
    public BannerModel findMuseumBanner(String museumId) {
        return this.jpaRepository.findById(museumId)
                .map(entity -> entity.entity.map(null))
                .orElse(new BannerModel(""));
    }
}
