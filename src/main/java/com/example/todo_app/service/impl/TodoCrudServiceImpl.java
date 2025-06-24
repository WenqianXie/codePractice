package com.example.todo_app.service.impl;

import com.example.todo_app.entity.TodoEntity;
import com.example.todo_app.exception.TodoNotFoundException;
import com.example.todo_app.mapper.TodoEntityMapper;
import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import com.example.todo_app.service.interfaces.TodoCrudService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TodoCrudServiceImpl implements TodoCrudService {
    private final TodoRepository todoRepository;
    private final TodoEntityMapper todoEntityMapper;

    public TodoCrudServiceImpl(TodoRepository todoRepository, TodoEntityMapper todoEntityMapper) {
        this.todoRepository = todoRepository;
        this.todoEntityMapper = todoEntityMapper;
    }

    @Override
    public List<Todo> getAllTodos() {
        List<TodoEntity> entities = todoRepository.findAllSorted();
        return todoEntityMapper.toModelList(entities);
    }

    @Override
    public Todo createTodo(Todo todo){
        todo.setCreatedAt(System.currentTimeMillis());
        TodoEntity entity = todoEntityMapper.toEntity(todo);
        TodoEntity savedEntity = todoRepository.save(entity);

        return todoEntityMapper.toModel(savedEntity);
    }

    @Override
    public Todo getTodoById(String id) {
        Optional<TodoEntity> optionalEntity = todoRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }
        return todoEntityMapper.toModel(optionalEntity.get());
    }

    @Override
    public Todo updateTodo(String id, Todo updatedTodo) {
        Optional<TodoEntity> optionalEntity = todoRepository.findById(id);

        if (optionalEntity.isEmpty()){
            throw new TodoNotFoundException("Cannot update - Todo not found with id: " + id);
        }

        TodoEntity existingEntity = optionalEntity.get();

        if (updatedTodo.getTitle() != null) {
            existingEntity.setTitle(updatedTodo.getTitle());
        }
        if (updatedTodo.getPriority() != null) {
            existingEntity.setPriority(updatedTodo.getPriority());
        }
        if (updatedTodo.getCompleted() != null) {
            existingEntity.setCompleted(updatedTodo.getCompleted());
        }

        TodoEntity savedEntity = todoRepository.save(existingEntity);

        return todoEntityMapper.toModel(savedEntity);
    }

    @Override
    public Todo deleteTodo(String id) {
        Optional<TodoEntity> optionalEntity = todoRepository.findById(id);

        if (optionalEntity.isEmpty()) {
            throw new TodoNotFoundException("Cannot delete - Todo not found with id: " + id);
        }

        TodoEntity entity = optionalEntity.get();
        Todo todo = todoEntityMapper.toModel(entity);
        todoRepository.delete(entity);
        return todo;
    }
}
