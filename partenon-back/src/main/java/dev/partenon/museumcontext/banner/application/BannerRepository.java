package dev.partenon.museumcontext.banner.application;

import dev.partenon.museumcontext.banner.doamin.MuseumBanner;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface BannerRepository extends ReactiveSortingRepository<MuseumBanner, Long> {

}
