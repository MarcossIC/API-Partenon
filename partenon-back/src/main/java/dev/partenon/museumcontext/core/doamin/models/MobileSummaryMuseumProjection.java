package dev.partenon.museumcontext.core.doamin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class MobileSummaryMuseumProjection {
    private String museumId;
    private String name;
    private String description;

    public MobileSummaryMuseumProjection(Long museumId, String name){
        this.museumId = String.valueOf(museumId);
        this.name = name;
    }

    public MobileSummaryMuseumProjection(Long museumId, String name, String description) {
        this.museumId = String.valueOf(museumId);
        this.name = name;
        this.description = description;
    }
}
