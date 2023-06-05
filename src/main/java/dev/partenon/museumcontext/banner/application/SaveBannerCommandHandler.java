package dev.partenon.museumcontext.banner.application;

import dev.partenon.global.domain.abstractcomponents.command.CommandHandler;
import dev.partenon.museumcontext.banner.doamin.MuseumBanner;
import dev.partenon.museumcontext.banner.doamin.SaveBannerCommand;
import dev.partenon.museumcontext.core.infrastructure.persistence.MuseumRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static dev.partenon.global.infrastructure.UtilReactor.catchingQueryTimeoutException;


@Service
@AllArgsConstructor
/**Maneja el comando de SaveBannerResource*/
public class SaveBannerCommandHandler implements CommandHandler<SaveBannerCommand> {
    @Autowired
    private MuseumRepository museumRepository;
    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public void handle(SaveBannerCommand command) {
        Mono.just(command.getMuseumId())
                .flatMap(museumRepository::findByMuseumId)
                .subscribeOn(Schedulers.immediate())
                .switchIfEmpty(Mono.error(new RuntimeException("ID No registrado")))
                .flatMap(museum -> bannerRepository.save(MuseumBanner.create(command.getMuseumBanner(), museum))
                        .retryWhen(catchingQueryTimeoutException));
    }
}
