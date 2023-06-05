package dev.partenon.security.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Value
public class Errors implements Serializable {
    private String code;
    private String message;
    private List<Error> errors = new ArrayList<>();

    @JsonCreator
    public Errors(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void add(String path, String code, String message) {
        this.errors.add(new Error(path, code, message));
    }
}
