package com.wikicoding.todo_ms.services;

import com.wikicoding.todo_ms.domain.Todo;
import com.wikicoding.todo_ms.domain.TodoFactory;
import com.wikicoding.todo_ms.domain.exceptions.NotFoundException;
import com.wikicoding.todo_ms.domain.usecases.DeleteTodoById;
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
public class DeleteTodoByIdService implements DeleteTodoById {
    private final TodoRepository repository;
    private final TodoMapper todoMapper;
    private RabbitTemplate rabbitTemplate;
    private Queue queue;

    @Override
    public Todo deleteTodoById(int todoId, String email) {
        Optional<TodoModel> todoModel = repository.findById(todoId);
        if (todoModel.isEmpty()) throw new NotFoundException("Todo not found");
        repository.deleteById(todoId);

        // send notification
        sendNotification(email, todoModel.get().getDescription());

        return todoMapper.dataModelToDomain(todoModel.get());
    }

    private void sendNotification(String userEmail, String taskDescription) {
        log.info("Sending email to {}", userEmail);
        String subject = "Completed Task" + taskDescription;
        String body = "<h1>Completed</h1>";

        NotificationMessage notificationMessage = new NotificationMessage(userEmail, subject, body);

        rabbitTemplate.convertAndSend(queue.getName(), notificationMessage);
    }
}
