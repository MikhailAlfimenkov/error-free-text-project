package com.example.errorfreetext.service.impl;

import com.example.errorfreetext.exception.TaskNotFoundException;
import com.example.errorfreetext.dto.CreationTaskRequest;
import com.example.errorfreetext.dto.TaskResponse;
import com.example.errorfreetext.entity.Task;
import com.example.errorfreetext.enums.Status;
import com.example.errorfreetext.mapper.TaskMapper;
import com.example.errorfreetext.repository.TaskRepository;
import com.example.errorfreetext.service.ChunkOptionsService;
import com.example.errorfreetext.service.ChunkTextSplitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ChunkOptionsService chunkOptionsService;
    @Mock
    private ChunkTextSplitter chunkTextSplitter;
    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private CreationTaskRequest request;

    @BeforeEach
    void setUp() {
        request = new CreationTaskRequest();
        request.setText("Привед.");
        request.setLang("RU");
    }

    @Test
    void createTask() {
        when(chunkTextSplitter.splitTextIntoChunks(any())).thenReturn(List.of("Привет."));
        when(chunkOptionsService.containsUrl(any())).thenReturn(false);
        when(chunkOptionsService.containsDigits(any())).thenReturn(false);

        UUID taskId = taskService.createTask(request);

        assertNotNull(taskId);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void getTaskById() {
        UUID taskId = UUID.randomUUID();
        Task task = new Task();
        task.setId(taskId);
        task.setStatus(Status.NEW);

        TaskResponse expectedResponse = new TaskResponse();
        expectedResponse.setId(taskId);
        expectedResponse.setStatus(Status.NEW);

        when(taskRepository.findByIdWithChunks(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.toTaskResponse(task)).thenReturn(expectedResponse);

        TaskResponse response = taskService.getTaskById(taskId);

        assertNotNull(response);
        assertEquals(taskId, response.getId());
        assertEquals(Status.NEW, response.getStatus());
    }

    @Test
    void getTaskById_NotFound_ThrowsTaskNotFoundException() {
        UUID randomId = UUID.randomUUID();
        when(taskRepository.findByIdWithChunks(randomId)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(randomId));
    }


}