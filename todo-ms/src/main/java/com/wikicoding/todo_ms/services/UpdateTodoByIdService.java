package com.wikicoding.todo_ms.services;

import com.wikicoding.todo_ms.domain.Todo;
import com.wikicoding.todo_ms.domain.TodoFactory;
import com.wikicoding.todo_ms.domain.exceptions.NotFoundException;
import com.wikicoding.todo_ms.domain.usecases.UpdateTodoById;
import com.wikicoding.todo_ms.domain.value_objects.TodoComplete;
import com.wikicoding.todo_ms.domain.value_objects.TodoDescr;
import com.wikicoding.todo_ms.domain.value_objects.TodoId;
import com.wikicoding.todo_ms.dto.TodoMapper;
import com.wikicoding.todo_ms.repository.TodoRepository;
import com.wikicoding.todo_ms.repository.datamodel.TodoModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateTodoByIdService implements UpdateTodoById {
    private final TodoRepository repository;
    private final TodoFactory todoFactory;
    private final TodoMapper todoMapper;

    @Override
    public Todo updateTodoById(int todoId, TodoDescr todoDescr, TodoComplete todoComplete) {
        Optional<TodoModel> todoModel = repository.findById(todoId);
        if (todoModel.isEmpty()) throw new NotFoundException("Todo not found");
        TodoModel todoModelToUpdate = todoModel.get();
        TodoId todoIdObj = new TodoId(todoId);

        Todo todo = todoFactory.createTodo(todoIdObj, todoDescr, todoComplete);
        todoModelToUpdate.updateTodo(todo);

        TodoModel saved = repository.save(todoModelToUpdate);

        return todoMapper.dataModelToDomain(saved);
    }
}
