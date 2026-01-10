package by.ezer.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class PagedResult<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public PagedResult(List<T> content, int page, int size, long totalElements) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
    }
}
