package dev.partenon.security.infrastructure.components;

import dev.partenon.security.domain.AuthService;
import dev.partenon.security.domain.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialService implements AuthService {
    private final AuthenticationManager manager;

    @Override
    public String authenticate(String email, String credential) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(email, credential)
        );
        var principal = (SecurityUser) authentication.getPrincipal();
        return principal.getUser().id();
    }
}
