package dev.partenon.global.domain.exceptions;

public class ContentInUseException extends RuntimeException {
    public String handler;

    public ContentInUseException(String message, String handler) {
        super(message);
        this.handler = handler;
    }
}
