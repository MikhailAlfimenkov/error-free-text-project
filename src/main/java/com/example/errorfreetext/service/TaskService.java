package com.example.errorfreetext.service;

import com.example.errorfreetext.dto.CreationTaskRequest;
import com.example.errorfreetext.dto.TaskResponse;
import com.example.errorfreetext.entity.Task;
import com.example.errorfreetext.enums.Status;

import java.util.UUID;


public interface TaskService {

    UUID createTask(CreationTaskRequest request);

    TaskResponse getTaskById(UUID id);

    void completeTask(UUID taskId);

    void updateTaskProgress(UUID taskId);

    void failTask(UUID taskId, Exception exception);

    Task findFirstTaskWithStatus(Status status);
}
