package dev.partenon.security.infrastructure.components;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomAuthenticationManager implements AuthenticationManager {
    private final SecurityUserService service;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.isAuthenticated()) {
            return authentication;
        }
        String usernameOrEmail = authentication.getPrincipal().toString();
        String credential = authentication.getCredentials().toString();//Token JWT
        try {
            var user = this.service.loadUserByUsernameOrEmail(usernameOrEmail);

            if (user == null) throw new UsernameNotFoundException("User not found");

            return new UsernamePasswordAuthenticationToken(user, credential, null);
        } catch (Exception e) {
            throw new AuthenticationServiceException("Error authenticating user, error: ", e);
        }
    }

}
