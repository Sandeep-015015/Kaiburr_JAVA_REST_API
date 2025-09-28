package com.example.Task1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tasks") // Maps this object to the 'tasks' MongoDB collection
public class Task {
    @Id // Maps to MongoDB's _id field
    private String id;
    private String name;
    private String owner;
    private String command; // Shell command to be run
    private List<TaskExecution> taskExecutions; // List of historical executions
}