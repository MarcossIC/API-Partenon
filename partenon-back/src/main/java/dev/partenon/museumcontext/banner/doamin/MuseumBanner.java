package dev.partenon.museumcontext.banner.doamin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.museumcontext.core.doamin.Museum;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/**Entidad Banner del museo*/
public class MuseumBanner implements Serializable {
    public static final Long serialVersionUID = 1L;

    @JsonIgnore
    private Long bannerId;
    @JsonIgnore
    private Museum museum;
    private String banner;

    public static MuseumBanner create(String banner, Museum museum){
        var museumBanner = new MuseumBanner();

        museumBanner.setBanner(banner);
        museumBanner.setMuseum(museum);

        return museumBanner;
    }

    @Override
    public String toString() {
        return "MuseumBanner{" +
                "museumBannerId=" + bannerId +
                ", banner='" + banner + '\'' +
                '}';
    }
}
