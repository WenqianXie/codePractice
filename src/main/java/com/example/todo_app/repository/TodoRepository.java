package com.example.todo_app.repository;

import com.example.todo_app.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    List<TodoEntity> findByCompleted(boolean completed);
    List<TodoEntity> findByPriority(String priority);
    long countByCompleted(boolean completed);

    @Query("SELECT t FROM TodoEntity t ORDER BY "
            + "CASE t.priority "
            + " WHEN 'HIGH' THEN 1"
            + " WHEN 'MEDIUM' THEN 2 "
            + " WHEN 'LOW' THEN 3"
            + " END, "
            + "t.createdAt ASC")
    List<TodoEntity> findAllSorted();
}
