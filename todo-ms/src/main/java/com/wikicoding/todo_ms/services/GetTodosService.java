package com.wikicoding.todo_ms.services;

import com.wikicoding.todo_ms.domain.Todo;
import com.wikicoding.todo_ms.domain.TodoFactory;
import com.wikicoding.todo_ms.domain.usecases.GetTodos;
import com.wikicoding.todo_ms.dto.TodoMapper;
import com.wikicoding.todo_ms.repository.TodoRepository;
import com.wikicoding.todo_ms.repository.datamodel.TodoModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetTodosService implements GetTodos {
    private final TodoRepository repository;
    private final TodoFactory todoFactory;

    public Page<Todo> getTodos(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TodoModel> data = repository.findByUserEmail(email, pageable);

        return new TodoMapper(todoFactory).listDataModelToDomain(data);
    }
}
