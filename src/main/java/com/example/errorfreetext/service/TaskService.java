package com.example.errorfreetext.service;

import com.example.errorfreetext.dto.CreationTaskRequest;
import com.example.errorfreetext.dto.TaskResponse;
import java.util.UUID;


public interface TaskService {

    UUID createTask(CreationTaskRequest request);

    TaskResponse getTaskById(UUID id);

}
