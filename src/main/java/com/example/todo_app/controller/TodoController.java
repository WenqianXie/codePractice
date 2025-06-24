package com.example.todo_app.controller;

import com.example.todo_app.dto.CompleteTodoResponseDto;
import com.example.todo_app.dto.UpdateTodoRequestDto;
import com.example.todo_app.model.Todo;
import com.example.todo_app.service.interfaces.TodoCompletionService;
import com.example.todo_app.service.interfaces.TodoCrudService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.example.todo_app.dto.CreateTodoRequestDto;
import com.example.todo_app.dto.TodoResponseDto;
import com.example.todo_app.mapper.TodoDtoMapper;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoCrudService todoCrudService;
    private final TodoCompletionService todoCompletionService;
    private final TodoDtoMapper todoDtoMapper;

    public TodoController(TodoCrudService todoCrudService, TodoCompletionService todoCompletionService, TodoDtoMapper todoDtoMapper) {
        this.todoCrudService = todoCrudService;
        this.todoCompletionService = todoCompletionService;
        this.todoDtoMapper = todoDtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        List<Todo> todos = todoCrudService.getAllTodos();
        List<TodoResponseDto> response = todos.stream()
                .map(todoDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody CreateTodoRequestDto request) {
        Todo todo = todoDtoMapper.toModel(request);
        Todo created = todoCrudService.createTodo(todo);
        return ResponseEntity.ok(todoDtoMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable String id,
                                                      @RequestBody UpdateTodoRequestDto request) {
        Todo updateData = todoDtoMapper.toModel(request);
        Todo updated = todoCrudService.updateTodo(id, updateData);
        return ResponseEntity.ok(todoDtoMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodoResponseDto> deleteTodo(@PathVariable String id) {
        Todo deleted = todoCrudService.deleteTodo(id);
        return ResponseEntity.ok(todoDtoMapper.toResponse(deleted));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<CompleteTodoResponseDto> completeTodo(@PathVariable String id) {
        Todo completed = todoCompletionService.completeTodo(id);
        TodoResponseDto todoDto = todoDtoMapper.toResponse(completed);
        CompleteTodoResponseDto response = new CompleteTodoResponseDto(
                todoDto,
                completed.getEarnedPoints(),
                "Todo completed! You earned " + completed.getEarnedPoints() + " points!"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getTodoById(@PathVariable String id) {
        Todo todo = todoCrudService.getTodoById(id);
        return ResponseEntity.ok(todoDtoMapper.toResponse(todo));
    }
}

