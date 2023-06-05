package dev.partenon.security.infrastructure.components;

import dev.partenon.security.domain.SecurityUser;
import dev.partenon.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Servicio personalizado de UserDetails(Servicio de Spring Security)
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityUserService implements UserDetailsService {
    private final UserRepository repository;

    /**
     * Busca un usuario personalizado con su username o email como credencial
     *
     * @param usernameOrEmail Email o Username del usuario a buscar
     * @return Un {@link UserDetails} usuario para Spring Security
     * @throws UsernameNotFoundException si el usuario no se encuentra
     */
    public UserDetails loadUserByUsernameOrEmail(String usernameOrEmail) {
        var user = repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return SecurityUser.userBuilder(user.get());
    }

    /**
     * Busca un usuario personalizado con un username como credencial
     *
     * @param username Username del usuario que desee buscar
     * @return Un {@link UserDetails} usuario para Spring Security
     * @throws UsernameNotFoundException si el usuario no se encuentra
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return SecurityUser.userBuilder(user.get());
    }
}
