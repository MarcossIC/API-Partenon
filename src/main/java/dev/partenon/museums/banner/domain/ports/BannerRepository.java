package dev.partenon.museums.banner.domain.ports;

import dev.partenon.museums.banner.domain.models.BannerModel;

public interface BannerRepository {

    BannerModel saveBanner(BannerModel model);

    BannerModel findMuseumBanner(String museumId);
}
