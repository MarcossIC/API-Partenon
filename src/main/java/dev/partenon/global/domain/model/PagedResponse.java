package dev.partenon.global.domain.model;

import dev.partenon.global.domain.abstractcomponents.query.Response;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase para almacenar los valores del output de la paginacion
 *
 * @param <T> Este parámetro cambia según el objeto que se pagine
 */
@Data
@NoArgsConstructor
@Builder
public final class PagedResponse<T> implements Response {
    //Contenido de la paginacion
    private List<T> content;
    //Numero de pagina actual
    private int page;
    //Tamaño de pagina actual
    private int size;
    //Elementos totales traidos
    private long totalElements;
    //True: Si es la ultima pagina, False: Si no es la ultima pagina
    private boolean last;

    public PagedResponse(List<T> content, int page, int size, long totalElements, boolean last) {
        setContent(content);
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.last = last;
    }

    public List<T> getContent() {
        return content == null ? null : new ArrayList<>(content);
    }

    public void setContent(List<T> content) {
        if (content == null) {
            this.content = null;
        } else {
            this.content = Collections.unmodifiableList(content);
        }
    }

    public boolean isLast() {
        return last;
    }
}
