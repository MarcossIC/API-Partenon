package dev.partenon.museums.banner.application;

import dev.partenon.global.domain.exceptions.ContentNotFoundException;
import dev.partenon.global.domain.ports.command.CommandHandler;
import dev.partenon.museums.banner.domain.actions.SaveBannerCommand;
import dev.partenon.museums.banner.domain.models.BannerModel;
import dev.partenon.museums.banner.domain.ports.BannerRepository;
import dev.partenon.museums.core.domain.ports.MuseumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SaveBannerCommandHandler implements CommandHandler<SaveBannerCommand> {
    private final BannerRepository repository;
    private final MuseumRepository museumRepository;

    @Override
    public void handle(SaveBannerCommand command) {
        if (!this.museumRepository.museumExistsTo(command.getMuseumId(), command.getUserId()))
            throw new ContentNotFoundException("Tu identificacion no tiene permitido o no pertenece al museo.", "SaveContactCommandHandler");

        var banner = this.repository.findMuseumBanner(command.getMuseumId());

        if (!command.getMuseumBanner().equals(banner.banner()))
            this.repository.saveBanner(new BannerModel(command.getMuseumId(), command.getMuseumBanner()));
    }
}
