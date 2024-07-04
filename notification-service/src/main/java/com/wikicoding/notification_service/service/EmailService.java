package com.wikicoding.notification_service.service;

import com.wikicoding.notification_service.domain.EmailSenderUseCase;
import com.wikicoding.notification_service.infra.EmailGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements EmailSenderUseCase {
    private final EmailGateway mailSenderGateway;

    @Override
    public void sendEmail(String destinationEmail, String subject, String body) {
        mailSenderGateway.sendEmail(destinationEmail, subject, body);
    }
}
