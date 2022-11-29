package dev.partenon.museumcontext.core.doamin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.partenon.expositions.domain.Expositions;
import dev.partenon.global.domain.abstractcomponents.query.Response;
import dev.partenon.museumcontext.banner.doamin.MuseumBanner;
import dev.partenon.museumcontext.contact.doamin.entity.MuseumContact;
import dev.partenon.museumcontext.description.doamin.MuseumDescription;
import dev.partenon.museumcontext.openinghours.doamin.OpeningHours;
import dev.partenon.museumcontext.plan.doamin.MuseumPlan;
import dev.partenon.museumcontext.appointment.domain.Appointment;
import dev.partenon.museumcontext.tours.domain.entity.MuseumTour;
import dev.partenon.user.domain.User;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/**Entidad Museo*/
public final class Museum implements Serializable, Response {
    public static final Long serialVersionUID = 1L;
    private Long museumId;
    private String museumName;
    private String province;
    private String city;
    private String street;
    private String addressNumber;
    @JsonIgnore
    private User user;
    private OpeningHours openingHours;
    private List<MuseumContact> museumContacts;
    private List<Expositions> expositions;
    private MuseumPlan museumPlan;
    private MuseumBanner museumBanner;
    private MuseumDescription museumDescription;
    private List<MuseumTour> tours;
    private List<Appointment> appointments;

    public Museum(Long museumId) {
        this.museumId = museumId;
    }

    public static Museum create(SaveMuseumAndUserCommand command, PasswordEncoder passwordEncoder){
        var museum = new Museum();
        museum.setMuseumName(StringUtils.stripAccents(command.getMuseumName()));
        museum.setProvince(command.getProvince());
        museum.setCity(command.getCity());
        museum.setStreet(command.getStreet());
        museum.setAddressNumber(command.getAddressNumber());

        var user = new User();
        user.setEmail(command.getEmail());
        String password = passwordEncoder.encode(command.getPassword());
        user.setPassword(password);
        user.setUsername(command.getUsername());
        museum.setUser(user);

        return museum;
    }

    @Override
    public String toString() {
        return "Museum{" +
                "museumName='" + museumName + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", addressNumber='" + addressNumber + '\'' +
                ", openingHours=" + openingHours +
                ", museumContacts=" + museumContacts +
                ", expositions=" + expositions +
                ", museumPlan=" + museumPlan +
                ", museumBanner=" + museumBanner +
                ", museumDescription=" + museumDescription +
                '}';
    }
}
