package com.example.todo_app.exception;

public class TodoAlreadyCompletedException extends RuntimeException {
    public TodoAlreadyCompletedException(String message) {
        super(message);
    }
}