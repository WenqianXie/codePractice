package com.example.todo_app.service.interfaces;

import com.example.todo_app.model.Todo;

import java.util.Map;

public interface TodoCompletionService {
    Todo completeTodo(String id);
}
