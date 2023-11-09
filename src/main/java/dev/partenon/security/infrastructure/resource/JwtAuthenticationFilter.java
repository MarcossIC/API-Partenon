package dev.partenon.security.infrastructure.resource;

import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.security.domain.SecurityUser;
import dev.partenon.security.domain.SecurityUtils;
import dev.partenon.user.domain.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AbstractJWT<User> jwtProvider;
    private final UserDetailsService accountDetailsService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {

        //Recupera el token del header
        final String authorizationHeader = request.getHeader(SecurityUtils.AUTH_HEADER);

        //Si el header esta vacio no realiza el filtro
        if (!StringUtils.hasText(authorizationHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = SecurityUtils.cleanBearer(authorizationHeader);
        final String email = jwtProvider.extractSubject(token);

        var securityUser = (SecurityUser) this.accountDetailsService.loadUserByUsername(email);
        this.jwtProvider.isTokenValid(token, securityUser.getUser());

        if (
                SecurityContextHolder.getContext().getAuthentication() == null
        ) {
            //UsernamePasswordAuthenticationToken es una instancia de Authentication
            var authUser = new UsernamePasswordAuthenticationToken(
                    securityUser.getUser(),
                    securityUser.getUser().password(),
                    securityUser.getAuthorities()
            );

            authUser.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authUser);
        }

        filterChain.doFilter(request, response);
    }
}

