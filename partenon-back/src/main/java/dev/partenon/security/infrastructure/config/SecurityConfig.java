package dev.partenon.security.infrastructure.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.partenon.security.infrastructure.components.CustomSecurityContextRepository;
import dev.partenon.user.domain.model.AuthUserRestModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

@Log4j2
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    /**
     * Configura la seguridad de las peticiones Http
     *
     * @param http Parámetro para cofigurar las peticiones
     * @throws Exception Arroja una excepcion generica
     */
    @Profile("security")
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http,
                                                      ReactiveAuthenticationManager authenticationManager,
                                                      CustomSecurityContextRepository securityContextRepository) {

        return http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .logout().disable()
                .cors().disable()
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() ->
                        swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED) ) )
                .accessDeniedHandler((swe, e) -> Mono.fromRunnable(() ->
                        swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)) )
                .and()
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers(HttpMethod.GET,
                                "/api/museums/**",
                                "/api/expositions/**",
                                "/api/tours/**",
                                "/api/mobil/**").permitAll()
                        .pathMatchers("/", "/favicon.ico",
                                "/**/*.png",
                                "/**/*.gif",
                                "/**/*.svg",
                                "/**/*.jpg",
                                "/**/*.html",
                                "/**/*.css",
                                "/**/*.js").permitAll()
                        .anyExchange().authenticated())
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setMaxAge(3600l);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        log.info("configured cors: {}", source);
        return source;
    }

    /*'
    @Bean
    public AuthenticationWebFilter loginFilter(ReactiveAuthenticationManager authenticationManager,
                                               ServerSecurityContextRepository serverSecurityContextRepository,
                                               ObjectMapper objectMapper) {
        var filter = new AuthenticationWebFilter(authenticationManager);
        filter.setSecurityContextRepository(serverSecurityContextRepository);
        filter.setServerAuthenticationConverter(exchange ->
                exchange.getRequest().getBody()
                        .cache()
                        .next()
                        .flatMap(buffer -> {
                            try {
                                AuthUserRestModel request = objectMapper.readValue(buffer.asInputStream(), AuthUserRestModel.class);
                                return Mono.just(request);
                            } catch (IOException e) {
                                log.debug("Can't read login request from JSON");
                                return Mono.error(e);
                            }
                        })
                        .map(request -> new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword()))
        );
        filter.setRequiresAuthenticationMatcher(pathMatchers(HttpMethod.POST, "/login"));
        filter.setAuthenticationSuccessHandler((webFilterExchange, authentication) -> {
            webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatus.OK);
            var user = (UserDetails) authentication.getPrincipal();
            var data = Map.of("name", user.getUsername() );
            try {
                var db = new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(data));
                return webFilterExchange.getExchange().getResponse().writeWith(Mono.just(db));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return Mono.empty();
            }
        });
        filter.setAuthenticationFailureHandler((webFilterExchange, e) -> {
            webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return webFilterExchange.getExchange().getResponse().setComplete();
        });

        return filter;
    }

     */
}
