package com.wikicoding.notification_service.infra;

public interface EmailGateway {
    void sendEmail(String destinationEmail, String subject, String body);
}
