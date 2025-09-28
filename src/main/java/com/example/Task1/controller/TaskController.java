package com.example.Task1.controller;

import com.example.Task1.model.Task;
import com.example.Task1.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Collections;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET tasks (All or by ID parameter)
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(@RequestParam(required = false) String id) {
        if (id != null) {
            return taskService.findById(id)
                .map(task -> ResponseEntity.ok(Collections.singletonList(task)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        return ResponseEntity.ok(taskService.findAll());
    }

    // GET tasks by name
    @GetMapping("/search")
    public List<Task> getTasksByName(@RequestParam String name) {
        return taskService.findTasksByName(name);
    }
    
    // POST task (Creation)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        return taskService.save(task);
    }

    // DELETE task (by ID)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String id) {
        taskService.deleteById(id);
    }

    // PUT TaskExecution (Execute Shell Command)
    // FIX: This ensures only one @PutMapping annotation exists for this path.
    @PutMapping("/{id}/execute") 
    public ResponseEntity<?> executeTask(@PathVariable String id) {
        try {
            Task updatedTask = taskService.executeTask(id);
            return ResponseEntity.ok(updatedTask);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Execution Error: " + e.getMessage());
        }
    }
}