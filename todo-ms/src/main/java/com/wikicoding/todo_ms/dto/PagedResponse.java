package com.wikicoding.todo_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PagedResponse<T> {
    private Iterable<T> list;
    private int size;
    private int totalPages;
}
