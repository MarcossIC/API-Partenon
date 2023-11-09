package dev.partenon.museums.banner.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerJpaRepository extends JpaRepository<BannerEntity, String> {

}
