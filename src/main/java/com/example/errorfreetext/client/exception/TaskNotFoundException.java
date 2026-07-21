package com.example.errorfreetext.client.exception;

import java.util.UUID;

public class TaskNotFoundException extends AppException {
    public TaskNotFoundException(UUID id) {
        super("Task with id: " + id + " not found", 40401);
    }
}
