package com.wikicoding.todo_ms.domain.usecases;

import com.wikicoding.todo_ms.domain.Todo;
import com.wikicoding.todo_ms.domain.value_objects.TodoComplete;
import com.wikicoding.todo_ms.domain.value_objects.TodoDescr;
import com.wikicoding.todo_ms.domain.value_objects.TodoId;

public interface AddTodo {
    Todo addTodo(TodoId todoId, TodoDescr todoDescr, TodoComplete todoComplete);
}
