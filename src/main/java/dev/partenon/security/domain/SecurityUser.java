package dev.partenon.security.domain;

import dev.partenon.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;

/**
 * Clase con los datos necesarios para autenticar un usuario
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityUser implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    private final User user;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean enabled;

    public SecurityUser(User user, Boolean enabled) {
        this.user = user;
        this.enabled = enabled;
    }

    public static UserDetails userBuilder(User user) {
        return new SecurityUser(
                user,
                true);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities == null ? null : authorities.stream().toList();
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
