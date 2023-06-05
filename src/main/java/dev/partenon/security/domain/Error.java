package dev.partenon.security.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

import java.io.Serializable;

@Value
public class Error implements Serializable {
    private String path;
    private String code;
    private String message;

    @JsonCreator
    Error(String path, String code, String message) {
        this.path = path;
        this.code = code;
        this.message = message;
    }
}
