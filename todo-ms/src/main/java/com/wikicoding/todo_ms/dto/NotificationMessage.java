package com.wikicoding.todo_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class NotificationMessage implements Serializable {
    private final String destinationEmail;
    private final String subject;
    private final String body;
}
