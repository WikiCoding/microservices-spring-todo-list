package com.wikicoding.todo_ms.domain.usecases;

import com.wikicoding.todo_ms.domain.Todo;
import org.springframework.data.domain.Page;

public interface GetTodos {
    Page<Todo> getTodos(String email, int size, int page);
}
