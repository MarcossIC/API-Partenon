package dev.partenon.museums.core.infrastructure.persistence;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.expositions.infrastructure.persistence.ExpositionsEntity;
import dev.partenon.global.domain.ports.Mapper;
import dev.partenon.museums.banner.infrastructure.persistence.BannerEntity;
import dev.partenon.museums.contact.infrastructure.persistence.ContactEntity;
import dev.partenon.museums.core.domain.models.Museum;
import dev.partenon.museums.plan.infrastructure.persistence.PlanEntity;
import dev.partenon.museums.schedule.infrastructure.persistence.entity.ScheduleEntity;
import dev.partenon.user.infrastructure.persistence.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "museum")
public class MuseumEntity implements Serializable {
    public static final Long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "province", nullable = false)
    private String province;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "address_number", nullable = false)
    private String addressNumber;
    @Column(name = "description", nullable = false)
    private String description;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(optional = false, targetEntity = UserEntity.class)
    private UserEntity userEntity;

    private ScheduleEntity scheduleEntity;
    private List<ContactEntity> contacts;
    private List<ExpositionsEntity> expositions;
    private PlanEntity planEntity;

    @OneToOne(mappedBy = "museum")
    private BannerEntity bannerEntity;

    @Transient
    @JsonIgnore
    @ToString.Exclude
    public final Mapper<Void, Museum> entity = (nothing) ->
            new Museum(this.getId(), this.getUserEntity().getUserId(), this.getProvince(), this.getProvince(), this.getCity(), this.getStreet(), this.getAddressNumber(), this.getDescription());

    @Transient
    @JsonIgnore
    @ToString.Exclude
    public static final Mapper<Museum, MuseumEntity> model = model -> {
        var entity = new MuseumEntity();
        entity.setId(model.id());
        entity.setUserEntity(UserEntity.builder().userId(model.userId()).build());
        entity.setName(model.name());
        entity.setProvince(model.province());
        entity.setCity(model.city());
        entity.setStreet(model.street());
        entity.setAddressNumber(model.addressNumber());
        entity.setDescription(model.description());
        return entity;
    };

    public MuseumEntity(String id) {
        this.setId(id);
    }

    @Override
    public String toString() {
        return "Museum{" +
                "name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", addressNumber='" + addressNumber + '\'' +
                ", openingHours=" + scheduleEntity +
                ", museumContacts=" + contacts +
                ", expositions=" + expositions +
                ", museumPlan=" + planEntity +
                ", museumBanner=" + bannerEntity +
                ", museumDescription=" + description +
                '}';
    }

}
