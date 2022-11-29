package dev.partenon.security.infrastructure.components;

import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.security.domain.CustomUserDetails;
import dev.partenon.user.domain.User;
import dev.partenon.user.domain.model.TokenModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SignInService {
    @Autowired
    private final CustomAuthenticationManager authenticationManager;
    private final AbstractJWT provider;

    public Mono<TokenModel> signIn(String usernameOrEmail, String password){
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(usernameOrEmail, password);
            var authenticate = authenticationManager.authenticate(authenticationToken);
            var user = authenticate.map(auth -> (CustomUserDetails)auth.getPrincipal()).block();

            var security = ReactiveSecurityContextHolder.getContext();
            authenticate.subscribe(auth-> security
                    .doOnNext(context-> context.setAuthentication(auth)));

            return Mono.just(createCredentials(new User(user.getUserId(), user.getUsername()), user.getMuseumId()));
        } catch (Exception ex){
            return Mono.empty();
        }
    }

    private TokenModel createCredentials(User user, Long museumId){
        return new TokenModel(
                String.valueOf(museumId),
                provider.createAccessToken(user, "http://localhost:8080/auth/user/login"),
                provider.createRefreshToken(user) );
    }
}
