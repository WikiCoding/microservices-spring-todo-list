package com.wikicoding.todo_ms.controller;

import com.wikicoding.todo_ms.domain.Todo;
import com.wikicoding.todo_ms.domain.usecases.AddTodo;
import com.wikicoding.todo_ms.domain.usecases.DeleteTodoById;
import com.wikicoding.todo_ms.domain.usecases.GetTodos;
import com.wikicoding.todo_ms.domain.usecases.UpdateTodoById;
import com.wikicoding.todo_ms.domain.value_objects.TodoComplete;
import com.wikicoding.todo_ms.domain.value_objects.TodoDescr;
import com.wikicoding.todo_ms.domain.value_objects.TodoId;
import com.wikicoding.todo_ms.dto.TodoMapper;
import com.wikicoding.todo_ms.dto.TodoRequest;
import com.wikicoding.todo_ms.dto.TodoResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/todos")
@AllArgsConstructor
public class TodoController {
    private final GetTodos getTodosService;
    private final AddTodo addTodoService;
    private final UpdateTodoById updateTodoByIdService;
    private final DeleteTodoById deleteTodoByIdService;
    private final TodoMapper todoMapper;

    @GetMapping
    public ResponseEntity<Iterable<TodoResponse>> getTodos() {
        Iterable<Todo> todoResponseList = getTodosService.getTodos();

        return ResponseEntity.status(HttpStatus.OK).body(todoMapper.listDomainToResponse(todoResponseList));
    }

    @PostMapping
    public ResponseEntity<TodoResponse> addTodo(@RequestBody TodoRequest todoRequest) {
        TodoId todoId = TodoId.builder().id(0).build();
        TodoDescr todoDescr = TodoDescr.builder().descr(todoRequest.getDescription()).build();
        TodoComplete todoComplete = TodoComplete.builder().complete(todoRequest.isCompleted()).build();
        Todo todo = addTodoService.addTodo(todoId, todoDescr, todoComplete);
        TodoResponse todoResponse = TodoResponse.builder().id(todo.getTodoId().getId())
                .description(todo.getTodoDescr().getDescr())
                .completed(todo.getTodoComplete().isComplete())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(todoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable("id") int id, @RequestBody TodoRequest todoRequest) {
        TodoDescr todoDescr = TodoDescr.builder().descr(todoRequest.getDescription()).build();
        TodoComplete todoComplete = TodoComplete.builder().complete(todoRequest.isCompleted()).build();
        Todo todo = updateTodoByIdService.updateTodoById(id, todoDescr, todoComplete);

        return ResponseEntity.status(HttpStatus.OK).body(todoMapper.domainToResponse(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodoResponse> deleteTodo(@PathVariable("id") int id) {
        Todo todo = deleteTodoByIdService.deleteTodoById(id);

        return ResponseEntity.status(HttpStatus.OK).body(todoMapper.domainToResponse(todo));
    }
}
