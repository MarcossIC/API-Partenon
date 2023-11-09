package dev.partenon.museums.core.domain.models;

import java.util.UUID;


public record Museum(
        String id,
        String userId,
        String name,
        String province,
        String city,
        String street,
        String addressNumber,
        String description
) {

    /**
     * @param userId        User ID del creador del museo
     * @param name          Nombre del museo
     * @param province      Provincia del musepp
     * @param city          Ciudad del museo
     * @param street        Calle del museo
     * @param addressNumber Numero de calle del museo
     * @param description   Descripcion del museo
     */
    public Museum(String userId,
                  String name,
                  String province,
                  String city,
                  String street,
                  String addressNumber,
                  String description) {
        this(UUID.randomUUID().toString(), userId, name, province, city, street, addressNumber, description);
    }
}
