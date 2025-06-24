package com.example.todo_app.dto;

public class CompleteTodoResponseDto {
    private TodoResponseDto todo;
    private Integer pointsEarned;
    private String message;

    public CompleteTodoResponseDto() {
    }

    public CompleteTodoResponseDto(TodoResponseDto todo, Integer pointsEarned, String message) {
        this.todo = todo;
        this.pointsEarned = pointsEarned;
        this.message = message;
    }

    public TodoResponseDto getTodo() {
        return todo;
    }

    public void setTodo(TodoResponseDto todo) {
        this.todo = todo;
    }

    public Integer getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(Integer pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}