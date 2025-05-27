package com.example.todo_app.model;

import java.util.List;
import java.util.ArrayList;

public class Todo {
    private Long id;
    private String title;
    private String priority;
    private Long createdAt;
    private List<String> tags;
    private Boolean completed;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPriority() {
        return priority;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public List<String> getTags() {
        return tags;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
