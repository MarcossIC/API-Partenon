package dev.partenon.security.domain;


public interface AuthService {
    String authenticate(String email, String credential);
}
