package com.example.errorfreetext.service.impl;

import com.example.errorfreetext.client.exception.TaskNotFoundException;
import com.example.errorfreetext.dto.CreationTaskRequest;
import com.example.errorfreetext.dto.TaskResponse;
import com.example.errorfreetext.entity.Task;
import com.example.errorfreetext.entity.TextChunk;
import com.example.errorfreetext.enums.Status;
import com.example.errorfreetext.mapper.TaskMapper;
import com.example.errorfreetext.repository.TaskRepository;
import com.example.errorfreetext.service.ChunkOptionsService;
import com.example.errorfreetext.service.ChunkTextSplitter;
import com.example.errorfreetext.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.errorfreetext.enums.Status.IN_PROGRESS;
import static com.example.errorfreetext.enums.Status.NEW;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ChunkOptionsService chunkOptionsService;
    private final ChunkTextSplitter chunkTextSplitter;

    @Override
    @Transactional
    public UUID createTask(CreationTaskRequest request) {

        UUID taskId = UUID.randomUUID();
        Task task = new Task();
        task.setId(taskId);
        task.setStatus(NEW);
        task.setCreatedAt(LocalDateTime.now());
        task.setCompletedChunksCount(0);
        List<String> splittedText = chunkTextSplitter.splitTextIntoChunks(request.getText());
        List<TextChunk> chunks = new ArrayList<>();

        for (int i = 0; i < splittedText.size(); i++) {
            String text = splittedText.get(i);

            TextChunk chunk = new TextChunk();
            chunk.setId(UUID.randomUUID());
            chunk.setStatus(NEW);
            chunk.setTextValue(text);
            chunk.setLang(request.getLang());
            chunk.setChunkOrder(i);
            chunk.setTask(task);

            boolean containsUrl = chunkOptionsService.containsUrl(text);
            boolean containsDigits = chunkOptionsService.containsDigits(text);
            chunk.setIgnoreDigits(containsDigits);
            chunk.setIgnoreUrls(containsUrl);

            chunks.add(chunk);
        }
        task.setChunks(chunks);
        taskRepository.save(task);
        return taskId;
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(UUID id) {
        Task task = taskRepository.findByIdWithChunks(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return switch (task.getStatus()) {
            case COMPLETED -> taskMapper.toCompletedResponse(task);
            case ERROR -> taskMapper.toErrorResponse(task);
            default -> taskMapper.toTaskResponse(task);
        };
    }

    @Override
    @Transactional
    public void completeTask(UUID taskId){
        Task task = findByIdWithChunks(taskId);
        if(Status.ERROR.equals(task.getStatus())) {
            return;
        }
        long completedCount = task.getChunks().stream()
                .filter(chunk -> Status.COMPLETED == chunk.getStatus())
                .count();
        long totalChunkSize = task.getChunks().size();
        if(completedCount == totalChunkSize) {
            task.setStatus(Status.COMPLETED);
            task.setCompletedAt(LocalDateTime.now());
            taskRepository.save(task);
            log.info("Task completed: {}", task.getId());
        }
    }

    @Override
    @Transactional
    public void updateTaskProgress(UUID taskId) {
        Task task = findByIdWithChunks(taskId);
        long count = task.getChunks().stream()
                .filter(chunk -> Status.COMPLETED == chunk.getStatus())
                .count();
        task.setCompletedChunksCount((int) count);
        taskRepository.save(task);
    }

    @Override
    public void failTask(UUID taskId, Exception exception){
        Task task = findByIdWithChunks(taskId);
        task.setStatus(Status.ERROR);
        task.setErrorMessage(exception.getMessage());
        taskRepository.save(task);
        log.info("Task {} failed.", task.getId());
    }


    @Override
    @Transactional
    public Task findFirstTaskWithStatus(Status status){
        List<Task> tasks = taskRepository.findTaskWithChunksByStatus(status, org.springframework.data.domain.PageRequest.of(0, 1));

        if(tasks.isEmpty()){
            return null;
        }
        Task task = tasks.get(0);
        task.setStatus(IN_PROGRESS);
        task.setCompletedChunksCount(0);
        taskRepository.save(task);
        return task;
    }

    private Task findByIdWithChunks(UUID taskId) {
        return taskRepository.findByIdWithChunks(taskId).orElseThrow(() ->
                new TaskNotFoundException(taskId));
    }
}
