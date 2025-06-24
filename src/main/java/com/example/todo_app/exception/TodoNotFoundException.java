package com.example.todo_app.exception;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(String message) {
        super(message);
    }

    public TodoNotFoundException(String id, String operation) {
        super(String.format("Todo with id %s not found for operation: %s", id, operation));
    }
}