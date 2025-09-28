package com.example.Task1.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Data 
@NoArgsConstructor
@AllArgsConstructor // <-- THIS is what generates the required constructor
public class TaskExecution {
    private Date startTime;
    private Date endTime;
    private String command; 
    private String output;  
}