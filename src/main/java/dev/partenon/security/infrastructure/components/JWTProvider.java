package dev.partenon.security.infrastructure.components;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.IncorrectClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.security.domain.TokenType;
import dev.partenon.user.domain.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
@Primary
public class JWTProvider implements AbstractJWT<User> {
    //Valores se encuentran el application.yml
    @Value("${jwt.secret}")
    private String secrectKey;
    @Value("${jwt.expired}")
    private long expDateDefined;

    /**
     * Vuelve a codificar la clave secreta
     */
    @PostConstruct
    protected void init() {
        //Vuelve a codificar la clave del token en base64
        this.secrectKey = Base64.getEncoder().encodeToString(secrectKey.getBytes());
    }

    @Override
    public String generateToken(User user, TokenType type) {
        return generateToken(user, type, null);
    }

    @Override
    public String generateToken(User user, TokenType type, Map<String, Object> extraClaims) {
        var NOW = new Date(System.currentTimeMillis());
        var extraTime = type.equals(TokenType.REFRESH) ? 259200000 : 0;
        return JWT.create()
                .withSubject(user.email())
                .withClaim("id", user.id())
                .withIssuedAt(NOW)
                .withExpiresAt(new Date(NOW.getTime() + expDateDefined + extraTime))
                .sign(signKey());
    }

    @Override
    public <S> S extractClaim(String token, String claimName, Class<S> type) {

        try {
            var claim = this.tokenDecoder(token).getClaim(claimName);
            if (claim.isNull())
                throw new IncorrectClaimException("The claim does not belong to the token", claimName, claim);
            return type.cast(claim);
        } catch (Exception e) {
            throw new JWTDecodeException("Token is invalid in 'extractSubject'");
        }
    }

    @Override
    public Boolean hasClaim(String token, String claimName) {
        var claim = this.tokenDecoder(token).getClaim(claimName);
        return !claim.isNull();
    }

    @Override
    public String extractSubject(String token) {
        try {
            return this.tokenDecoder(token).getSubject();
        } catch (Exception e) {
            throw new JWTDecodeException("Token is invalid in 'extractSubject'");
        }
    }

    @Override
    public Date extractExpired(String token) {
        return this.tokenDecoder(token).getExpiresAt();
    }

    @Override
    public void isTokenValid(String token, User user) {
        Assert.hasText(token, "The token is empty");
        Assert.notNull(this.extractClaim(token, "id", String.class), "Token ID is null");
        final String tokenEmail = this.extractSubject(token);
        if (user != null) Assert.isTrue(!tokenEmail.equals(user.email()), "User email does not match the email token");

        if (this.isTokenExpired(token)) {
            throw new TokenExpiredException("Token has already expired", this.extractExpired(token).toInstant());
        }
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return this.extractExpired(token).before(new Date());
    }

    private DecodedJWT tokenDecoder(String token) {
        try {
            return JWT.require(this.signKey()).build().verify(token);
        } catch (Exception ex) {
            throw new JWTVerificationException("Token is invalid");
        }
    }

    private Algorithm signKey() {
        return Algorithm.HMAC256(secrectKey.getBytes());
    }
}
