package com.example.Task1.service;

import com.example.Task1.model.Task;
import com.example.Task1.model.TaskExecution;
import com.example.Task1.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // --- CRUD and Find Methods ---
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(String id) {
        return taskRepository.findById(id);
    }

    public Task save(Task task) {
        // Ensure taskExecutions list is initialized for new tasks
        if (task.getTaskExecutions() == null) {
            task.setTaskExecutions(new ArrayList<>());
        }
        return taskRepository.save(task);
    }

    public void deleteById(String id) {
        taskRepository.deleteById(id);
    }

    public List<Task> findTasksByName(String name) {
        return taskRepository.findByNameContainingIgnoreCase(name);
    }

    // --- Command Execution Method ---
    public Task executeTask(String taskId) throws Exception {
        Task task = findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        // 1. **SECURITY VALIDATION:** (Crucial step from the task description)
        String command = task.getCommand();
        if (command == null || command.isEmpty()) {
            throw new IllegalArgumentException("Task command cannot be empty.");
        }
        // Basic blacklist check for malicious commands.
        // A robust solution would use whitelisting or sandboxing.
        String unsafeKeywords = "rm|sudo|chmod|kill|pkill|format|halt|shutdown";
        if (command.matches(".*\\b(" + unsafeKeywords + ")\\b.*")) {
             throw new SecurityException("Command contains unsafe/malicious code.");
        }

        // 2. Execution Setup
        Date startTime = new Date();
        StringBuilder output = new StringBuilder();

        // 3. Execute the command using ProcessBuilder
        try {
            // Uses 'bash -c' on Unix-like systems (Linux, macOS) or equivalent on Windows
            Process process = new ProcessBuilder("cmd.exe", "/c", command).start();
            
            // Read standard output
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            // Read standard error
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = errorReader.readLine()) != null) {
                    output.append("ERROR: ").append(line).append("\n");
                }
            }
            
            process.waitFor(); // Wait for the command to complete

        } catch (Exception e) {
            output.append("EXECUTION FAILED: ").append(e.getMessage());
        }

        // 4. Create and Save Execution Log
        Date endTime = new Date();
        TaskExecution execution = new TaskExecution(startTime, endTime, command, output.toString().trim());

        if (task.getTaskExecutions() == null) {
            task.setTaskExecutions(new ArrayList<>());
        }
        task.getTaskExecutions().add(execution);

        return taskRepository.save(task);
    }
}
