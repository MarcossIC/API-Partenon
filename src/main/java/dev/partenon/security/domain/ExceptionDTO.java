package dev.partenon.security.domain;

import lombok.Builder;

import java.util.Map;

@Builder
public class ExceptionDTO {
    public String type;
    public ExceptionCodes code;
    public Map<String, String> details;

}
