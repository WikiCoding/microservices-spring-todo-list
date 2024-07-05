package com.wikicoding.todo_ms.dto;

import com.wikicoding.todo_ms.domain.Todo;
import com.wikicoding.todo_ms.domain.TodoFactory;
import com.wikicoding.todo_ms.domain.value_objects.TodoComplete;
import com.wikicoding.todo_ms.domain.value_objects.TodoDescr;
import com.wikicoding.todo_ms.domain.value_objects.TodoId;
import com.wikicoding.todo_ms.domain.value_objects.TodoUserEmail;
import com.wikicoding.todo_ms.repository.datamodel.TodoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoMapper {
    private final TodoFactory todoFactory;

    public TodoMapper(TodoFactory todoFactory) {
        this.todoFactory = todoFactory;
    }

    public Todo dataModelToDomain(TodoModel todoModel) {
        return buildTodoObject(todoModel);
    }

    public Page<Todo> listDataModelToDomain(Page<TodoModel> todos) {
        List<Todo> todosRes = todos.stream().map(this::buildTodoObject).toList();

        Pageable pageable = todos.getPageable();

        return new PageImpl<>(todosRes, pageable, todos.getTotalElements());
    }

    public Iterable<TodoResponse> listDomainToResponse(Iterable<Todo> todos) {
        List<TodoResponse> todoResponses = new ArrayList<>();

        for (Todo todo : todos) {
            TodoResponse todoResponse = domainToResponse(todo);

            todoResponses.add(todoResponse);
        }

        return todoResponses;
    }

    public TodoResponse domainToResponse(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getTodoId().getId())
                .description(todo.getTodoDescr().getDescr())
                .completed(todo.getTodoComplete().isComplete())
                .userEmail(todo.getTodoUserEmail().getUserEmail())
                .build();
    }

    private Todo buildTodoObject(TodoModel todoModel) {
        TodoId todoId = new TodoId(todoModel.getId());
        TodoDescr todoDescr = new TodoDescr(todoModel.getDescription());
        TodoComplete todoComplete = new TodoComplete(todoModel.isCompleted());
        TodoUserEmail todoUserEmail = new TodoUserEmail(todoModel.getUserEmail());
        return todoFactory.createTodo(todoId, todoDescr, todoComplete, todoUserEmail);
    }
}
