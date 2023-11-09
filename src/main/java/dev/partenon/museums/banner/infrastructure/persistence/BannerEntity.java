package dev.partenon.museums.banner.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.global.domain.ports.Mapper;
import dev.partenon.museums.banner.domain.models.BannerModel;
import dev.partenon.museums.core.infrastructure.persistence.MuseumEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "museum_banner")
public class BannerEntity implements Serializable {
    public static final Long serialVersionUID = 1L;

    @Id
    private String museumId;

    @Column(name = "banner")
    private String banner;

    @OneToOne
    @JoinColumn(name = "museum_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    private MuseumEntity museum;

    @Transient
    @JsonIgnore
    @ToString.Exclude
    public final Mapper<Void, BannerModel> entity = (nothing) -> new BannerModel(this.getMuseumId(), this.getBanner());

    @Transient
    @JsonIgnore
    @ToString.Exclude
    public static final Mapper<BannerModel, BannerEntity> model = model -> {
        var museumBanner = new BannerEntity();
        museumBanner.setBanner(model.banner());
        museumBanner.setMuseumId(model.museumId());
        return museumBanner;
    };

    @Override
    public String toString() {
        return "MuseumBanner{" +
                "banner='" + banner + '\'' +
                '}';
    }
}
