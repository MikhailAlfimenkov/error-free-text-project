package com.example.errorfreetext.scheduler;


import com.example.errorfreetext.entity.Task;
import com.example.errorfreetext.entity.TextChunk;
import com.example.errorfreetext.enums.Status;
import com.example.errorfreetext.repository.TaskRepository;
import com.example.errorfreetext.service.ChunkService;
import com.example.errorfreetext.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.example.errorfreetext.enums.Status.IN_PROGRESS;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledTaskService {
    private final ChunkService chunkService;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @Scheduled(fixedDelayString = "5000", initialDelayString = "10000")
    public void processTask() {
        log.info("Start processing task.");

        Task task = taskService.findFirstTaskWithStatus(Status.NEW);
        if(task == null) {
            log.info("Task with status NEW not found.");
            return;
        }
        for (TextChunk chunk : task.getChunks()) {
            if(Status.ERROR.equals(chunk.getStatus())) {
                break;
            }
            chunkService.processChunk(chunk.getId());
        }
        taskService.completeTask(task.getId());
        log.info("Task {} processing finished", task.getId());

    }

}
