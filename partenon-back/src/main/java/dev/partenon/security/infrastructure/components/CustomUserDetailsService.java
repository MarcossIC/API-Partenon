package dev.partenon.security.infrastructure.components;

import dev.partenon.security.domain.CustomUserDetails;
import dev.partenon.user.application.UserRepository;
import dev.partenon.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Locale;


/**
 * Servicio personalizado de UserDetails(Servicio de Spring Security)
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetailsService implements ReactiveUserDetailsService {
    private final UserRepository repository;

    /**
     * Busca un usuario personalizado con un username o email como credencial
     *
     * @param usernameOrEmail Parametro para buscar el usuario
     * @return Un {@link CustomUserDetails} usuario personalizado para Srping Security
     * @throws UsernameNotFoundException
     */
    @Override
    public Mono<UserDetails> findByUsername(String usernameOrEmail) {
        String key = usernameOrEmail.toLowerCase(Locale.ROOT);
        return Mono.just(usernameOrEmail)
                .flatMap(event-> repository.findByUsernameOrEmail(event,event)
                        .subscribeOn(Schedulers.parallel()))
                .flatMap(event -> event != null ? (Mono<? extends UserDetails>) userBuilder(event) : Mono.empty() );

    }

    private UserDetails userBuilder(User user) {
        return new CustomUserDetails(
                user.getUserId(),
                user.getMuseum().getMuseumId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                true);
    }
}
