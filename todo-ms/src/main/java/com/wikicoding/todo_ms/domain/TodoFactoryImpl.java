package com.wikicoding.todo_ms.domain;

import com.wikicoding.todo_ms.domain.value_objects.TodoComplete;
import com.wikicoding.todo_ms.domain.value_objects.TodoDescr;
import com.wikicoding.todo_ms.domain.value_objects.TodoId;
import org.springframework.stereotype.Service;

@Service
public class TodoFactoryImpl implements TodoFactory {
    @Override
    public Todo createTodo(TodoId todoId, TodoDescr todoDescr, TodoComplete todoComplete) {
        return new Todo(todoId, todoDescr, todoComplete);
    }
}
