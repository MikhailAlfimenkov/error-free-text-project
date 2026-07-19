package com.example.errorfreetext.service;

import com.example.errorfreetext.dto.CreationTaskRequest;
import com.example.errorfreetext.dto.TaskResponse;
import com.example.errorfreetext.entity.Task;
import com.example.errorfreetext.entity.TextChunk;
import com.example.errorfreetext.entity.TaskStatus;
import com.example.errorfreetext.mapper.TaskMapper;
import com.example.errorfreetext.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    @Transactional
    public UUID createTask(CreationTaskRequest request) {

        UUID taskId = UUID.randomUUID();
        Task task = new Task();
        task.setId(taskId);
        task.setStatus(TaskStatus.NEW);
        task.setCreatedAt(LocalDateTime.now());

        List<TextChunk> chunks = new ArrayList<>();

        TextChunk chunk = new TextChunk();
        chunk.setId(UUID.randomUUID());
        chunk.setTextValue(request.getText());
        chunk.setLang(request.getLang());
        chunk.setStatus(TaskStatus.NEW);
        chunk.setChunkOrder(0);
        chunk.setTask(task);

        chunks.add(chunk);
        task.setChunks(chunks);

        taskRepository.save(task);
        return taskId;
    }
    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("task not found with id: " + id));
        return taskMapper.toResponse(task);
    }
}
