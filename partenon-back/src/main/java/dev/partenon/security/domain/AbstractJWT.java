package dev.partenon.security.domain;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.partenon.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

public abstract class AbstractJWT {

    public abstract String createAccessToken(User user, String url);
    public abstract Algorithm signKey();
    public abstract String createRefreshToken(User user);
    public abstract DecodedJWT decoderToken(String token);
    public abstract String getSubjectUsername(String token);

    /**
     * Verifica que el token sea valido
     *
     * @param token Token JWT
     * @return Devuelve true si es valido, false si no
     */
    public boolean validate(String token) {
        try {
            //Si no tiene texto no es valido
            if (!StringUtils.hasText(token)) return false;
            //Si arroja una excepcion no es valido
            var decodedToken = decoderToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String validateAuthorizationToken(String auth) {
        if(Boolean.TRUE.equals(StringUtils.hasText(auth) && auth.startsWith("Bearer "))){
            //Divide el token en la palabra Bearer y el token un array de dos elementos
            var divideToken = auth.split(" ");
            if(divideToken.length == 2)
                return divideToken[1];
        }
        return null;
    }

}
