package com.example.errorfreetext.service;

import com.example.errorfreetext.mapper.TaskMapper;
import com.example.errorfreetext.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

//    public void test() {
//        taskRepository.save();
//    }
}
