package dev.partenon.global.domain.exceptions;

public class ContentNotFoundException extends RuntimeException {

    public String handler;

    public ContentNotFoundException(String message, String handler) {
        super(message);
        this.handler = handler;
    }

}
