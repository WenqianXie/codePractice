package com.example.todo_app.service.interfaces;

import com.example.todo_app.model.Todo;

import java.util.List;

public interface TodoCrudService {
    List<Todo> getAllTodos();
    Todo getTodoById(String id);
    Todo createTodo(Todo todo);
    Todo updateTodo(String id, Todo todo);
    Todo deleteTodo(String id);
}
