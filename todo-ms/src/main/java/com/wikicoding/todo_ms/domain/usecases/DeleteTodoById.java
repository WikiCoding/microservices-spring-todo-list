package com.wikicoding.todo_ms.domain.usecases;

import com.wikicoding.todo_ms.domain.Todo;

public interface DeleteTodoById {
    Todo deleteTodoById(int todoId);
}
