package com.example.todo_app.service;

import com.example.todo_app.model.Todo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service  // Tell Spring this is a service component
public class TodoService {
    private Map<Long, Todo> todoStore = new HashMap<>();
    private Long nextId = 1L;
    private int totalPoints = 0;

    public List<Todo> getAllTodos() {
        // Get all todos from map
        List<Todo> todos = new ArrayList<>(todoStore.values());
        // Sort by priority (HIGH -> MEDIUM -> LOW), then by creation time
        todos.sort((todo1, todo2) -> {
            // First, compare by priority
            int priority1 = getPriorityValue(todo1.getPriority());
            int priority2 = getPriorityValue(todo2.getPriority());

            if (priority1 != priority2) {
                return priority2 - priority1;  // Higher priority first
            }

            // If same priority, compare by creation time
            return todo1.getCreatedAt().compareTo(todo2.getCreatedAt());  // Earlier first
        });

        return todos;
    }

    // Helper method to convert priority string to number
    private int getPriorityValue(String priority) {
        switch (priority) {
            case "HIGH":
                return 3;
            case "MEDIUM":
                return 2;
            case "LOW":
                return 1;
            default:
                return 0;
        }
    }

    public Todo createTodo(Todo todo){
        // Set ID
        todo.setId(nextId);
        nextId = nextId + 1;  // Increment for next todo

        // Set creation time
        todo.setCreatedAt(System.currentTimeMillis());

        // Store in map
        todoStore.put(todo.getId(), todo);

        // Return the created todo
        return todo;
    }

    // Get todo by ID
    public Todo getTodoById(Long id) {
        return todoStore.get(id);
    }

    public Todo updateTodo(Long id, Todo updatedTodo) {
        // Find existing todo
        Todo existingTodo = todoStore.get(id);

        if (existingTodo == null) {
            return null;
        }

        // Update fields (only if provided)
        if (updatedTodo.getTitle() != null) {
            existingTodo.setTitle(updatedTodo.getTitle());
        }
        if (updatedTodo.getPriority() != null) {
            existingTodo.setPriority(updatedTodo.getPriority());
        }
        if (updatedTodo.getTags() != null) {
            existingTodo.setTags(updatedTodo.getTags());
        }

        return existingTodo;
    }

    // Delete todo
    public Todo deleteTodo(Long id) {
        return todoStore.remove(id);  // Returns null if not found
    }

    public Map<String, Object> completeTodo(Long id){
        // Find the todo
        Todo todo = todoStore.get(id);

        if (todo == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Todo not found");
            return error;
        }

        // Check if already completed
        if (Boolean.TRUE.equals(todo.getCompleted())) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Todo already completed");
            return error;
        }
        // Mark as completed
        todo.setCompleted(true);

        // Calculate points for completed todos
        int points = calculatePoints(todo);
        // Add to total points
        totalPoints += points;

        // Return result
        Map<String, Object> result = new HashMap<>();
        result.put("todo", todo);
        result.put("pointsEarned", points);
        result.put("message", "Todo completed! You earned " + points + " points!");

        return result;
    }

    // Calculate points based on tags
    private int calculatePoints(Todo todo) {
        if (todo.getTags() == null || todo.getTags().isEmpty()) {
            return 10;  // Default points if no tags
        }
        int totalPoints = 0;

        // Calculate points for each tag
        for (String tag : todo.getTags()) {
            switch (tag.toLowerCase()) {  // Convert to lowercase for comparison
                case "sport":
                    totalPoints += 20;  // Highest points - hardest to maintain
                    break;
                case "study":
                    totalPoints += 15;  // High points - important for growth
                    break;
                case "work":
                    totalPoints += 10;  // Medium points - necessary
                    break;
                case "chores":
                    totalPoints += 8;   // Low points - routine tasks
                    break;
                case "entertainment":
                    totalPoints += 5;   // Lowest points - easy/fun
                    break;
                default:
                    totalPoints += 10;  // Default points for unknown tags
            }
        }

        return totalPoints;
    }

    // Get user statistics
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();

        // Count completed todos
        long completedCount = todoStore.values().stream()
                .filter(todo -> Boolean.TRUE.equals(todo.getCompleted()))
                .count();

        // Count total todos
        int totalCount = todoStore.size();

        // Build response
        stats.put("totalPoints", totalPoints);
        stats.put("totalTodos", totalCount);
        stats.put("completedTodos", completedCount);
        stats.put("completionRate", totalCount > 0 ? (completedCount * 100.0 / totalCount) + "%" : "0%");

        return stats;
    }
}
