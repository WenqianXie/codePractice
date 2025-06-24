package com.example.todo_app.dto;

import java.util.List;

public class CreateTodoRequestDto {
    private String title;
    private String priority;
    private List<String> tags;

    public CreateTodoRequestDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}