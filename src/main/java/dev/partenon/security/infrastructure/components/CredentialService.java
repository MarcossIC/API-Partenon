package dev.partenon.security.infrastructure.components;

import dev.partenon.security.domain.SecurityUser;
import dev.partenon.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CredentialService {
    private final CustomAuthenticationManager manager;

    public User generateCredentials(String usernameOrEmail, String password) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(usernameOrEmail, password);

            var authenticate = manager.authenticate(authToken);

            var user = (SecurityUser) authenticate.getPrincipal();

            SecurityContextHolder.getContext().setAuthentication(authenticate);
            return user.getUser();
        } catch (Exception ex) {
            return null;
        }
    }
}
