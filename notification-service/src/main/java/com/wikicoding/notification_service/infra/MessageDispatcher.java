package com.wikicoding.notification_service.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikicoding.notification_service.dto.NotificationMessage;
import com.wikicoding.notification_service.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues = "email")
@AllArgsConstructor
@Slf4j
public class MessageDispatcher {
    @Autowired
    private final EmailService emailService;

    @RabbitHandler
    public void receive(String in) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            NotificationMessage notificationMessage = objectMapper.readValue(in, NotificationMessage.class);
            log.info("Sending the following notification for Email Sender: {}", notificationMessage);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        // deactivated call since I don't really want to send emails for this demo.
//        emailService.sendEmail(in.getDestinationEmail(), in.getSubject(), in.getBody());
    }
}
