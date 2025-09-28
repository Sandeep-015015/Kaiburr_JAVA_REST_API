package com.example.Task1.repository;

import com.example.Task1.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    // Custom query method for finding tasks by name (case-insensitive search)
    List<Task> findByNameContainingIgnoreCase(String name);
}