package dev.partenon.security.infrastructure.config;

import dev.partenon.global.domain.exceptions.ContentNotFoundException;
import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.security.domain.SecurityUser;
import dev.partenon.security.infrastructure.components.CustomAuthenticationManager;
import dev.partenon.security.infrastructure.components.JWTProvider;
import dev.partenon.user.domain.ports.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Maneja la configuracion de los Beans
 */
@Configuration
@RequiredArgsConstructor
public class BeansConfig {
    private final UserRepository repository;

    @Primary
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Primary
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> repository
                .findByEmail(email)
                .map(SecurityUser.userToUserDetails)
                .orElseThrow(() -> new ContentNotFoundException("Email no se encuentra registrado", "userDetailsService"));
    }

}
