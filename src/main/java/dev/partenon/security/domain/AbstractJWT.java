package dev.partenon.security.domain;

import java.util.Date;
import java.util.Map;

public interface AbstractJWT<T> {

    /**
     * Crea un token JWT con los datos del Usuario
     *
     * @param userDetails Datos del usuario que crea el token
     * @return Token JWT
     */
    String generateToken(T userDetails, TokenType type);

    /**
     * Crea un token los datos del Usuario mas otros detalles
     *
     * @param user        Datos del usuario que crea el token
     * @param extraClaims Detalles extra para el token
     * @return Token JWT
     */
    String generateToken(T user, final TokenType type, Map<String, Object> extraClaims);

    /**
     * Recupera una claim especifica del token
     *
     * @param token     Token JWT
     * @param claimName Nombre de la claim que desea recuperar
     * @param type      Tipo de dato de la claim
     * @param <S>       Cambia segun el tipo de dato de la claim
     */
    <S> S extractClaim(String token, String claimName, Class<S> type);

    /**
     * Verifica si la claim que deseas se encuentra en el token
     *
     * @param token     Token JWT
     * @param claimName Nombre de la claim
     */
    Boolean hasClaim(String token, String claimName);

    /**
     * Recupera la credencial principal del usuario que creo el token
     * (En nuestro caso Email)
     *
     * @param token Token JWT
     */
    String extractSubject(String token);

    /**
     * Recupera la fecha de caducidad del token
     *
     * @param token Token JWT
     */
    Date extractExpired(String token);

    /**
     * Verifica que el token sea valido
     *
     * @param token       Token JWT
     * @param userDetails Usuario que pertenece el token
     */
    void isTokenValid(String token, T userDetails);

    /**
     * Verifica si el token ha caducado
     *
     * @param token Token JWT
     */
    Boolean isTokenExpired(String token);
}
