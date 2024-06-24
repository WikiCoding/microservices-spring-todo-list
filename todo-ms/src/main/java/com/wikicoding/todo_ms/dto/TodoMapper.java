package com.wikicoding.todo_ms.dto;

import com.wikicoding.todo_ms.domain.Todo;
import com.wikicoding.todo_ms.domain.TodoFactory;
import com.wikicoding.todo_ms.domain.value_objects.TodoComplete;
import com.wikicoding.todo_ms.domain.value_objects.TodoDescr;
import com.wikicoding.todo_ms.domain.value_objects.TodoId;
import com.wikicoding.todo_ms.repository.datamodel.TodoModel;
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

    public Iterable<Todo> listDataModelToDomain(Iterable<TodoModel> todos) {
        List<Todo> todosRes = new ArrayList<>();

        for (TodoModel todoModel : todos) {
            Todo todoRes = buildTodoObject(todoModel);
            todosRes.add(todoRes);
        }

        return todosRes;
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
                .build();
    }

    private Todo buildTodoObject(TodoModel todoModel) {
        TodoId todoId = new TodoId(todoModel.getId());
        TodoDescr todoDescr = new TodoDescr(todoModel.getDescription());
        TodoComplete todoComplete = new TodoComplete(todoModel.isCompleted());
        return todoFactory.createTodo(todoId, todoDescr, todoComplete);
    }
}
