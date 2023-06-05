package dev.partenon.security.infrastructure.components;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.partenon.security.domain.AbstractJWT;
import dev.partenon.user.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

/**
 * Clase encargada de manejar todo lo relacionado al Token JWT
 */
@Service
public class JWTProvider extends AbstractJWT {
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

    /**
     * Crea el algoritmo para encriptar el token y llava para decifrarlo
     */
    @Override
    public Algorithm signKey() {
        return Algorithm.HMAC256(secrectKey.getBytes());
    }


    /**
     * Crea el accessToken
     *
     * @param user Usuario que crea el token
     * @param url  URL desde donde se esta creando
     * @return Devuelve un AccessToken
     */
    @Override
    public String createAccessToken(User user, String url) {
        var NOW = new Date(System.currentTimeMillis());
        return com.auth0.jwt.JWT.create()
                .withSubject(user.getUsername())
                .withClaim("id", user.getUserId().toString())
                .withIssuedAt(NOW)
                .withExpiresAt(new Date(NOW.getTime() + expDateDefined))
                .withIssuer(url)
                .sign(signKey());
    }

    /**
     * Crea el refresh Token
     *
     * @param user Usuario que crea el token
     * @return Devuelve un RefeshToken
     */
    @Override
    public String createRefreshToken(User user) {
        var NOW = new Date(System.currentTimeMillis());
        return com.auth0.jwt.JWT.create()
                .withSubject(user.getUsername())
                .withClaim("id", user.getUserId().toString())
                .withIssuedAt(NOW)
                .withExpiresAt(new Date(NOW.getTime() + expDateDefined + 259200000))
                .sign(signKey());
    }

    /**
     * Decodifica el token JWT usando el algoritmo de decodificacion
     *
     * @param token Token JWT
     * @return {@link  DecodedJWT} Es la clase que almacena el token decodificado
     */
    @Override
    public DecodedJWT decoderToken(String token) {
        return com.auth0.jwt.JWT.require(this.signKey()).build().verify(token);
    }


    /**
     * Recupera el Subject del Token (Siempre sera el username)
     *
     * @param token Token JWT
     * @return Username
     */
    @Override
    public String getUsername(String token) {
        try {
            return decoderToken(token).getSubject();
        } catch (Exception e) {
            return "bad token";
        }
    }

    /**
     * Recupera el ID del Usuario del token
     *
     * @param token Token JWT
     * @return UserId
     */
    @Override
    public String getUserId(String token) {
        try {
            return decoderToken(token).getClaim("id").toString();
        } catch (Exception e) {
            return "bad token";
        }
    }
}
