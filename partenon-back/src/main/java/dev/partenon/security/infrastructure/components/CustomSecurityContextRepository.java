package dev.partenon.security.infrastructure.components;

import dev.partenon.security.domain.AbstractJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomSecurityContextRepository implements ServerSecurityContextRepository {

    private final AbstractJWT tokenRepository;
    private final CustomAuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext sc) {
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = extractAuthorizationToken(exchange);
        if (token == null) return Mono.empty();

        var username = tokenRepository.getSubjectUsername(token);
        return username != null ? createSecurityContext(username, token) : Mono.error(new Exception("Error to load security context with token"));
    }

    private Mono<SecurityContext> createSecurityContext(String username, String token) {
        Authentication auth = new UsernamePasswordAuthenticationToken(username, token, null);
        return authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
    }

    private String extractAuthorizationToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) return null;

        return tokenRepository.validateAuthorizationToken(authHeader);
    }

}
