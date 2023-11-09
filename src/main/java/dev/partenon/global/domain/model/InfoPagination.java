package dev.partenon.global.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

/**
 * Clase para almacenar los parámetros de la Paginación
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public final class InfoPagination {
    //Numero de pagina actual
    private Integer index;
    //Tamaño de pagina
    private Integer size;
    //Se ordena en base a esta propiedad
    private String sort;
    //Orden puede ser ASC || DESC
    private String order;

    public static InfoPagination build(Integer index, Integer size, String sort, String order) {
        var paginationInfo = new InfoPagination(index, size, sort, order);
        paginationInfo.validatePage();
        return paginationInfo;
    }

    /**
     * Comprueba que lo datos de paginacion tengan sentido
     */
    private void validatePage() {
        StringBuilder errorMessasage = new StringBuilder();
        if (this.getIndex() < 0) errorMessasage.append("Page number cannot be less than zero.").append("\n");
        if (this.getSize() < 0) errorMessasage.append("Size number cannot be less than zero.").append("\n");
        if (this.getSize() > 12)
            errorMessasage.append("Page size must not be greater than ").append(12).append(".").append("\n");
        if (!this.getOrder().equals("asc") && !this.getOrder().equals("desc"))
            errorMessasage.append("Order not found.").append("\n");

        if (StringUtils.hasText(errorMessasage.toString()))
            throw new IllegalArgumentException(errorMessasage.toString());
    }
}
