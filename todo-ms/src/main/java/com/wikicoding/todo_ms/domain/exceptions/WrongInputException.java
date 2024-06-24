package com.wikicoding.todo_ms.domain.exceptions;

public class WrongInputException extends RuntimeException {
    public WrongInputException(String message) {
        super(message);
    }
}
