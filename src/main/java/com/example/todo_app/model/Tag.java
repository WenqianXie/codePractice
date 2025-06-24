package com.example.todo_app.model;

public enum Tag {
    SPORT(20),
    STUDY(15),
    WORK(10),
    CHORES(8),
    ENTERTAINMENT(5);

    private final int points;

    Tag(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public static Tag fromString(String tag) {
        try {
            return Tag.valueOf(tag.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}