package com.example.todo_app.service.interfaces;

import com.example.todo_app.model.Todo;

public interface PointCalculationService {
    int calculatePoints(Todo todo);
    int getTotalPoints();
    void addPoints(int points);
}
