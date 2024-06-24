package com.wikicoding.todo_ms.domain.usecases;

import com.wikicoding.todo_ms.domain.Todo;
import com.wikicoding.todo_ms.domain.value_objects.TodoComplete;
import com.wikicoding.todo_ms.domain.value_objects.TodoDescr;

public interface UpdateTodoById {
    Todo updateTodoById(int todoId, TodoDescr todoDescr, TodoComplete todoComplete);
}
