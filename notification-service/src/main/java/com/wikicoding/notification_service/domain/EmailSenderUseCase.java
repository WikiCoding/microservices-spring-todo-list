package com.wikicoding.notification_service.domain;

public interface EmailSenderUseCase {
    void sendEmail(String destination, String subject, String body);
}
