package com.wikicoding.todo_ms.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikicoding.todo_ms.domain.Todo;
import com.wikicoding.todo_ms.domain.TodoFactory;
import com.wikicoding.todo_ms.domain.exceptions.NotFoundException;
import com.wikicoding.todo_ms.domain.usecases.UpdateTodoById;
import com.wikicoding.todo_ms.domain.value_objects.TodoComplete;
import com.wikicoding.todo_ms.domain.value_objects.TodoDescr;
import com.wikicoding.todo_ms.domain.value_objects.TodoId;
import com.wikicoding.todo_ms.domain.value_objects.TodoUserEmail;
import com.wikicoding.todo_ms.dto.NotificationMessage;
import com.wikicoding.todo_ms.dto.TodoMapper;
import com.wikicoding.todo_ms.repository.TodoRepository;
import com.wikicoding.todo_ms.repository.datamodel.TodoModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UpdateTodoByIdService implements UpdateTodoById {
    private final TodoRepository repository;
    private final TodoFactory todoFactory;
    private final TodoMapper todoMapper;
    private RabbitTemplate rabbitTemplate;
    private Queue queue;

    @Override
    public Todo updateTodoById(int todoId, TodoDescr todoDescr, TodoComplete todoComplete, TodoUserEmail todoUserEmail) {
        Optional<TodoModel> todoModel = repository.findById(todoId);
        if (todoModel.isEmpty()) throw new NotFoundException("Todo not found");
        TodoModel todoModelToUpdate = todoModel.get();
        TodoId todoIdObj = new TodoId(todoId);

        Todo todo = todoFactory.createTodo(todoIdObj, todoDescr, todoComplete, todoUserEmail);
        todoModelToUpdate.updateTodo(todo);

        if (todoComplete.isComplete()) {
            try {
                sendNotification(todoUserEmail.getUserEmail(), todoDescr.getDescr());
            } catch (JsonProcessingException e) {
                log.error("Error processing JSON");
            }
        }

        TodoModel saved = repository.save(todoModelToUpdate);

        return todoMapper.dataModelToDomain(saved);
    }

    private void sendNotification(String userEmail, String taskDescription) throws JsonProcessingException {
        log.info("Sending email to {}", userEmail);
        String subject = "Completed Task: " + taskDescription;
        String body = "<h1>Completed</h1>";

        NotificationMessage notificationMessage = new NotificationMessage(userEmail, subject, body);

        ObjectMapper objectMapper = new ObjectMapper();
        String messageJson = objectMapper.writeValueAsString(notificationMessage);

        rabbitTemplate.convertAndSend(queue.getName(), messageJson);
    }
}
