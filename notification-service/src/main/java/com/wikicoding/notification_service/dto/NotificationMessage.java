package com.wikicoding.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage implements Serializable {
    private String destinationEmail;
    private String subject;
    private String body;

    @Override
    public String toString() {
        return "NotificationMessage{" +
                "destinationEmail='" + destinationEmail + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
