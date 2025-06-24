package com.example.todo_app.service.impl;

import com.example.todo_app.entity.TodoEntity;
import com.example.todo_app.exception.TodoAlreadyCompletedException;
import com.example.todo_app.exception.TodoNotFoundException;
import com.example.todo_app.mapper.TodoEntityMapper;
import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import com.example.todo_app.service.interfaces.PointCalculationService;
import com.example.todo_app.service.interfaces.TodoCompletionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TodoCompletionServiceImpl implements TodoCompletionService {
    private final TodoRepository todoRepository;
    private final TodoEntityMapper todoEntityMapper;
    private final PointCalculationService pointCalculationService;

    public TodoCompletionServiceImpl(TodoRepository todoRepository, TodoEntityMapper todoEntityMapper, PointCalculationService pointCalculationService) {
        this.todoRepository = todoRepository;
        this.todoEntityMapper = todoEntityMapper;
        this.pointCalculationService = pointCalculationService;
    }

    @Override
    public Todo completeTodo(String id) {
        Optional<TodoEntity> optionalEntity = todoRepository.findById(id);

        if (optionalEntity.isEmpty()) {
            throw new TodoNotFoundException("Cannot complete - Todo not found with id: " + id);
        }

        TodoEntity entity = optionalEntity.get();

        if (Boolean.TRUE.equals(entity.getCompleted())) {
            throw new TodoAlreadyCompletedException("Todo with id " + id + " is already completed");
        }

        entity.setCompleted(true);

        Todo tempTodo = todoEntityMapper.toModel(entity);
        int points = pointCalculationService.calculatePoints(tempTodo);

        entity.setEarnedPoints(points);
        TodoEntity savedEntity = todoRepository.save(entity);

        Todo todo = todoEntityMapper.toModel(savedEntity);

        // 更新总积分
        ((PointCalculationServiceImpl) pointCalculationService).addPoints(points);

        return todo;
    }
}
