package dev.partenon.global.domain;

public class EntityNotFoundException extends Exception {

    /**
     * Crea una excepcion especial en caso de no encontrar la entidad buscada
     *
     * @param entity     Nombre de la entidad que arroja la excepcion
     * @param identifier Identificador con el que se intento buscar
     */
    public EntityNotFoundException(String entity, String identifier) {
        super(String.format("No <%s> entity found with identifier <%s> ", entity, identifier));
    }

}
