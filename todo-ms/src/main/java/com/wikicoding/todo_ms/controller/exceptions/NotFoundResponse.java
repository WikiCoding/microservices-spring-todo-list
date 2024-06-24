package com.wikicoding.todo_ms.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotFoundResponse {
    private int status;
    private String message;
    private long timestamp;
}
