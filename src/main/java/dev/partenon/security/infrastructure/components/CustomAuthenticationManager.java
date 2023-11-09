package dev.partenon.security.infrastructure.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Primary
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationManager implements AuthenticationManager {
    private final UserDetailsService service;
    private final PasswordEncoder encoder;

    //
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.isAuthenticated()) return authentication;

        try {
            final String email = authentication.getPrincipal().toString();
            var accountAuthenticated = this.service.loadUserByUsername(email);

            final String password = authentication.getCredentials().toString();
            this.verifyPasswords(password, accountAuthenticated.getPassword());


            var auth = UsernamePasswordAuthenticationToken.authenticated(
                    accountAuthenticated,
                    accountAuthenticated.getPassword(),
                    accountAuthenticated.getAuthorities()
            );

            auth.setDetails(authentication.getDetails());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return auth;
        } catch (Exception e) {
            throw new AuthenticationServiceException("Error authenticating user, error: ", e);
        }
    }

    /**
     * Verifica que la contraseña sin codificar y la guardada sean iguales
     *
     * @param rawPassword     Contraseña sin codificar
     * @param encodedPassword Contraseña Condificada
     */
    private void verifyPasswords(String rawPassword, String encodedPassword) {
        if (!this.encoder.matches(rawPassword, encodedPassword))
            throw new RuntimeException("La contraseña no es correcta");
    }

}
