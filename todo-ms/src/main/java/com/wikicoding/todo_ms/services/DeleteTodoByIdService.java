package com.wikicoding.todo_ms.services;

import com.wikicoding.todo_ms.domain.Todo;
import com.wikicoding.todo_ms.domain.TodoFactory;
import com.wikicoding.todo_ms.domain.exceptions.NotFoundException;
import com.wikicoding.todo_ms.domain.usecases.DeleteTodoById;
import com.wikicoding.todo_ms.dto.TodoMapper;
import com.wikicoding.todo_ms.repository.TodoRepository;
import com.wikicoding.todo_ms.repository.datamodel.TodoModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteTodoByIdService implements DeleteTodoById {
    private final TodoRepository repository;
    private final TodoFactory todoFactory;
    private final TodoMapper todoMapper;

    @Override
    public Todo deleteTodoById(int todoId) {
        Optional<TodoModel> todoModel = repository.findById(todoId);
        if (todoModel.isEmpty()) throw new NotFoundException("Todo not found");
        repository.deleteById(todoId);

        return todoMapper.dataModelToDomain(todoModel.get());
    }
}
