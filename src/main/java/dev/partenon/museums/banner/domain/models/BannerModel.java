package dev.partenon.museums.banner.domain.models;


public record BannerModel(String museumId, String banner) {
    public BannerModel(String banner) {
        this(null, "");
    }
}
