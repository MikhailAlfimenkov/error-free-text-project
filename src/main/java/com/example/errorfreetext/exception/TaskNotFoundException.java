package com.example.errorfreetext.exception;

import java.util.UUID;

public class TaskNotFoundException extends AppException {
    public TaskNotFoundException(UUID id) {
        super("Task with id: " + id + " not found", 40401);
    }
}
