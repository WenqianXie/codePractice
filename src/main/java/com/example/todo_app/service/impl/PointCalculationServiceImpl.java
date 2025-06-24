package com.example.todo_app.service.impl;

import com.example.todo_app.model.Todo;
import com.example.todo_app.model.Tag;
import com.example.todo_app.service.interfaces.PointCalculationService;
import org.springframework.stereotype.Service;

@Service
public class PointCalculationServiceImpl implements PointCalculationService {
    private int totalPoints = 0;

    @Override
    public int calculatePoints(Todo todo) {
        if (todo.getTags() == null || todo.getTags().isEmpty()) {
            return 0;
        }

        return todo.getTags().stream()
                .map(Tag::fromString)
                .filter(tag -> tag != null)
                .mapToInt(Tag::getPoints)
                .sum();
    }

    @Override
    public int getTotalPoints() {
        return totalPoints;
    }

    @Override
    public void addPoints(int points) {
        this.totalPoints += points;
    }
}