package dev.partenon.museumcontext.contact.doamin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactRestModel {
    private String contact;
    private String type;
}
