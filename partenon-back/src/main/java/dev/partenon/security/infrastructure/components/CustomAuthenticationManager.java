package dev.partenon.security.infrastructure.components;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomAuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTProvider jwtUtil;
    private final CustomUserDetailsService userDetails;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if(authentication.isAuthenticated()) return Mono.just(authentication);

        var usernameOrEmail = authentication.getPrincipal().toString();
        var token = authentication.getCredentials().toString();
        try {
            Mono<UserDetails> userDetailsMono = userDetails.findByUsername(usernameOrEmail);
            return userDetailsMono
                    .filter(Objects::nonNull)
                    .map(user -> new UsernamePasswordAuthenticationToken(user, token, null));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

}
