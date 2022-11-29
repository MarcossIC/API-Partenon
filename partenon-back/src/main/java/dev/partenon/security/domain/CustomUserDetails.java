package dev.partenon.security.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;

/**
 * Clase con los datos necesarios para autenticar un usuario
 */
@Data
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long userId;
    private Long museumId;
    private String username;
    private String password;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean enabled;

    public CustomUserDetails(Long userId,
                             Long museumId,
                             String username,
                             String password,
                             String email,
                             Boolean enable) {
        this.userId = userId;
        this.museumId = museumId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enable;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
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
