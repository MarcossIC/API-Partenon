package dev.partenon.global.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase para almacenar los parámetros de la Paginación
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public final class Page {
    //Numero de pagina actual
    private Integer index;
    //Tamaño de pagina
    private Integer size;
    //Se ordena en base a esta propiedad
    private String sort;
    //Orden puede ser ASC || DESC
    private String order;

    public static Page starter(Integer index, Integer size, String sort, String order) {
        return new Page(index, size, sort, order);
    }
}
