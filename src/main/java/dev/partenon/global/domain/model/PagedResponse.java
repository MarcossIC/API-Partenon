package dev.partenon.global.domain.model;

import dev.partenon.global.domain.ports.query.Response;
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
    private List<T> content;
    private Integer page;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
    private Boolean last;


    public PagedResponse(List<T> content, int page, int size, long totalElements, int totalPages, boolean last) {
        setContent(content);
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public List<T> getContent() {
        return (this.content == null || this.content.isEmpty())
                ? Collections.emptyList()
                : new ArrayList<>(this.content);
    }

    public void setContent(List<T> content) {
        if (content == null) this.content = Collections.emptyList();
        else this.content = Collections.unmodifiableList(content);
    }

    public Boolean isLast() {
        return last;
    }
}
