package dev.partenon.security.domain;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.partenon.user.domain.User;
import org.springframework.util.StringUtils;

public abstract class AbstractJWT {

    public abstract String createAccessToken(User user, String url);

    public abstract Algorithm signKey();

    public abstract String createRefreshToken(User user);

    public abstract DecodedJWT decoderToken(String token);

    public abstract String getUsername(String token);

    public abstract String getUserId(String token);

    /**
     * Verifica que el token sea valido
     *
     * @param token Token JWT
     * @return Devuelve true si es valido, false si no
     */
    public Boolean validateContentToken(String token) {
        var isValid = true;
        try {
            decoderToken(token);//Si arroja una excepcion no es valido
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }

    public String getToken(String auth) {
        var stripedToken = auth.split(" ");
        return stripedToken[1];
    }

    public Boolean validateBearerToken(String auth) {
        //Veridica que no este vacio y empiece en Bearer
        var isValid = (StringUtils.hasText(auth) && auth.startsWith("Bearer "));
        //Verifica que al separarlo tenga dos posiciones
        isValid = (auth.split(" ").length == 2);
        return isValid;
    }
}
