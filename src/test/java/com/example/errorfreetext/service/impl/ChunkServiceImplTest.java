package com.example.errorfreetext.service.impl;

import com.example.errorfreetext.client.YandexSpellerClient;
import com.example.errorfreetext.client.exception.YandexApiException;
import com.example.errorfreetext.dto.SpellResult;
import com.example.errorfreetext.entity.Task;
import com.example.errorfreetext.entity.TextChunk;
import com.example.errorfreetext.enums.Status;
import com.example.errorfreetext.repository.TextChunkRepository;
import com.example.errorfreetext.service.TaskService;
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
class ChunkServiceImplTest {

    @Mock
    private YandexSpellerClient yandexSpellerClient;
    @Mock
    private TaskService taskService;
    @Mock
    private TextChunkRepository textChunkRepository;

    @InjectMocks
    private ChunkServiceImpl chunkService;

    private UUID chunkId;
    private TextChunk textChunk;

    @BeforeEach
    void setUp() {
        chunkId = UUID.randomUUID();
        Task task = new Task();
        task.setId(UUID.randomUUID());

        textChunk = new TextChunk();
        textChunk.setId(chunkId);
        textChunk.setTextValue("Привед мир");
        textChunk.setLang("RU");
        textChunk.setTask(task);
        textChunk.setIgnoreDigits(true);
        textChunk.setIgnoreUrls(true);
    }

    @Test
    void processChunk() {
        when(textChunkRepository.findById(chunkId)).thenReturn(Optional.of(textChunk));

        SpellResult spellResult = new SpellResult();
        spellResult.setWord("Привед");
        spellResult.setPos(0);
        spellResult.setLen(6);
        spellResult.setS(List.of("Привет"));

        List<List<SpellResult>> apiResponse = List.of(List.of(spellResult));
        when(yandexSpellerClient.checkTexts(any())).thenReturn(apiResponse);

        chunkService.processChunk(chunkId);

        assertEquals("Привет мир", textChunk.getCorrectedValue());
        assertEquals(Status.COMPLETED, textChunk.getStatus());
        verify(taskService, times(1)).updateTaskProgress(textChunk.getTask().getId());
    }
    @Test
    void processChunk_ApiException_FailsTask(){
        when(textChunkRepository.findById(chunkId)).thenReturn(Optional.of(textChunk));
        YandexApiException apiException = new YandexApiException("Service Unavailable");
        when(yandexSpellerClient.checkTexts(any())).thenThrow(apiException);

        assertThrows(RuntimeException.class, () -> chunkService.processChunk(chunkId));
        assertEquals(Status.ERROR, textChunk.getStatus());
        verify(taskService, times(1)).failTask(eq(textChunk.getTask().getId()), any());
    }
}