package com.example.todo_app.controller;

import com.example.todo_app.model.Todo;
import com.example.todo_app.service.TodoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RestController  // Tells Spring this class handles REST API requests
@RequestMapping("/api/todos")  // Base URL for all methods in this class
public class TodoController {

    @Autowired  // Tell Spring to inject TodoService
    private TodoService todoService;

    @GetMapping("/test")  // Handles GET request to /api/todos/test
    public String test() {
        return "API works!";  // Returns plain text
    }

    // Get all todos
    @GetMapping  // No path means use base path: /api/todos
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    // Create new todo
    @PostMapping  // Handles POST requests to /api/todos
    public Todo createTodo(@RequestBody Todo todo){
        return todoService.createTodo(todo);
    }

    // Complete a todo
    @PutMapping("/{id}/complete") // PUT /api/todos/1/complete
    public Map<String, Object> completeTodo(@PathVariable Long id){
        return todoService.completeTodo(id);
    }

    // Get user statistics
    @GetMapping("/stats") // GET /api/todos/stats
    public Map<String, Object> getStats() {
        return todoService.getStats();
    }

    // Update a todo
    @PutMapping("/{id}")  // PUT /api/todos/1
    public Map<String, Object> updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        Todo updated = todoService.updateTodo(id, updatedTodo);

        if (updated == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Todo not found");
            return error;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("message", "Todo updated successfully");
        result.put("todo", updated);
        return result;
    }


    @DeleteMapping("/{id}")  // DELETE /api/todos/1
    public Map<String, Object> deleteTodo(@PathVariable Long id) {
        Todo deleted = todoService.deleteTodo(id);

        if (deleted == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Todo not found");
            return error;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("message", "Todo deleted successfully");
        result.put("deletedTodo", deleted);
        return result;
    }
}

