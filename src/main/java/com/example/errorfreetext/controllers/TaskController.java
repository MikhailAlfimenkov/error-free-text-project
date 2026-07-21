package com.example.errorfreetext.controllers;

import com.example.errorfreetext.dto.TaskResponse;
import com.example.errorfreetext.dto.CreationTaskRequest;
import com.example.errorfreetext.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<UUID> createTask(@Valid @RequestBody CreationTaskRequest request){
        UUID taskId = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable UUID id){
        TaskResponse response = taskService.getTaskById(id);
        return ResponseEntity.ok(response);
    }


}
