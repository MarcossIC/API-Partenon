package dev.partenon.security.domain;

import dev.partenon.user.domain.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.function.Function;

/**
 * Clase con los datos necesarios para autenticar un usuario
 */
@Data
@NoArgsConstructor
public class SecurityUser implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private User user;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean enabled;
    public static Function<User, UserDetails> userToUserDetails = (user) -> new SecurityUser(user, true);

    public SecurityUser(User user, Boolean enabled) {
        this.user = user;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities == null ? null : authorities.stream().toList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
