package com.wikicoding.todo_ms.services;

import com.wikicoding.todo_ms.domain.Todo;
import com.wikicoding.todo_ms.domain.TodoFactory;
import com.wikicoding.todo_ms.domain.usecases.GetTodos;
import com.wikicoding.todo_ms.dto.TodoMapper;
import com.wikicoding.todo_ms.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetTodosService implements GetTodos {
    private final TodoRepository repository;
    private final TodoFactory todoFactory;

    public Iterable<Todo> getTodos() {
        return new TodoMapper(todoFactory).listDataModelToDomain(repository.findAll());
    }
}
