package com.example.todo_app.mapper;

import com.example.todo_app.entity.TodoEntity;
import com.example.todo_app.model.Todo;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoEntityMapper {

    // Entity -> Model
    public Todo toModel(TodoEntity entity){
        if (entity == null) {
            return null;
        }

        Todo todo = new Todo();
        todo.setId(entity.getId());
        todo.setTitle(entity.getTitle());
        todo.setPriority(entity.getPriority());
        todo.setCompleted(entity.getCompleted());
        todo.setCreatedAt(entity.getCreatedAt());
        todo.setEarnedPoints(entity.getEarnedPoints());

        if (entity.getTags() != null && !entity.getTags().isEmpty()) {
            todo.setTags(Arrays.asList(entity.getTags().split(",")));
        }

        return todo;
    }

    // Model -> Entity
    public TodoEntity toEntity (Todo todo){
        if (todo == null){
            return null;
        }

        TodoEntity entity = new TodoEntity();
        entity.setId(todo.getId());
        entity.setTitle(todo.getTitle());
        entity.setPriority(todo.getPriority());
        entity.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : false);
        entity.setEarnedPoints(todo.getEarnedPoints());
        entity.setCreatedAt(todo.getCreatedAt());
        if (todo.getTags() != null && !todo.getTags().isEmpty()) {
            entity.setTags(String.join(",", todo.getTags()));
        }

        return entity;
    }

    // List<Entity> -> List<Model>
    public List<Todo> toModelList(List<TodoEntity> entities){
        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    // List<Model> → List<Entity>
    public List<TodoEntity> toEntityList(List<Todo> todos) {
        return todos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
