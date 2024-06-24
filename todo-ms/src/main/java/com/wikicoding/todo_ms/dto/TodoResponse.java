package com.wikicoding.todo_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TodoResponse {
    private final int id;
    private final String description;
    private final boolean completed;
}
