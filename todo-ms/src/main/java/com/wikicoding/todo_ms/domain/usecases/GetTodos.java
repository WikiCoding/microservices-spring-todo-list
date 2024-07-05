package com.wikicoding.todo_ms.domain.usecases;

import com.wikicoding.todo_ms.domain.Todo;

import java.awt.print.Pageable;

public interface GetTodos {
    Iterable<Todo> getTodos(String email, int size, int page);
}
