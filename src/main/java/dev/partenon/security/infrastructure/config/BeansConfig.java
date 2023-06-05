package dev.partenon.security.infrastructure.config;

import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.security.infrastructure.components.CustomAuthenticationManager;
import dev.partenon.security.infrastructure.components.JWTProvider;
import dev.partenon.security.infrastructure.components.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Maneja la configuracion de Seguridad de Spring
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BeansConfig {
    private static final String BASE_URL = "http://localhost:8080/";
    public static final int TIMEOUT = 1000;
    private final SecurityUserService service;
    private final CustomAuthenticationManager authenticationManager;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 2048, 2);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return this.authenticationManager;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return this.service;
    }

    @Bean
    public AbstractJWT jwtProvider() {
        return new JWTProvider();
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        return new JavaMailSenderImpl();
    }

}
