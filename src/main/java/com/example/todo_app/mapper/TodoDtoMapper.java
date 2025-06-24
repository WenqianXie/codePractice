package com.example.todo_app.mapper;

import com.example.todo_app.dto.CreateTodoRequestDto;
import com.example.todo_app.dto.TodoResponseDto;
import com.example.todo_app.dto.UpdateTodoRequestDto;
import com.example.todo_app.model.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoDtoMapper {

    // CreateTodoRequestDto → Todo (Model)
    public Todo toModel(CreateTodoRequestDto request) {
        if (request == null) {
            return null;
        }

        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setPriority(request.getPriority());
        todo.setTags(request.getTags());
        todo.setCompleted(false);

        return todo;
    }

    // UpdateTodoRequestDto → Todo (Model)
    public Todo toModel(UpdateTodoRequestDto request) {
        if (request == null) {
            return null;
        }

        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setPriority(request.getPriority());
        todo.setCompleted(request.getCompleted());
        todo.setTags(request.getTags());

        return todo;
    }

    // Todo (Model) → TodoResponseDto
    public TodoResponseDto toResponse(Todo todo) {
        if (todo == null) {
            return null;
        }

        TodoResponseDto response = new TodoResponseDto();
        response.setId(todo.getId());
        response.setTitle(todo.getTitle());
        response.setPriority(todo.getPriority());
        response.setCompleted(todo.getCompleted());
        response.setCreatedAt(todo.getCreatedAt());
        response.setTags(todo.getTags());
        response.setEarnedPoints(todo.getEarnedPoints());

        return response;
    }
}