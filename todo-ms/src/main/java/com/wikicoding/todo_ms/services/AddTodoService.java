package com.wikicoding.todo_ms.services;

import com.wikicoding.todo_ms.domain.Todo;
import com.wikicoding.todo_ms.domain.TodoFactory;
import com.wikicoding.todo_ms.domain.usecases.AddTodo;
import com.wikicoding.todo_ms.domain.value_objects.TodoComplete;
import com.wikicoding.todo_ms.domain.value_objects.TodoDescr;
import com.wikicoding.todo_ms.domain.value_objects.TodoId;
import com.wikicoding.todo_ms.dto.TodoMapper;
import com.wikicoding.todo_ms.repository.TodoRepository;
import com.wikicoding.todo_ms.repository.datamodel.TodoModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddTodoService implements AddTodo {
    private final TodoRepository repository;
    private final TodoFactory todoFactory;
    private final TodoMapper todoMapper;

    @Override
    public Todo addTodo(TodoId todoId, TodoDescr todoDescr, TodoComplete todoComplete) {
        Todo todo = todoFactory.createTodo(todoId, todoDescr, todoComplete);

        TodoModel todoModel = new TodoModel(todo);

        TodoModel saved = repository.save(todoModel);

        return todoMapper.dataModelToDomain(saved);
    }
}
