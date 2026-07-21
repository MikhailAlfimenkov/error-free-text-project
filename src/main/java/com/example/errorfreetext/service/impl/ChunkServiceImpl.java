package com.example.errorfreetext.service.impl;

import com.example.errorfreetext.client.YandexSpellerClient;
import com.example.errorfreetext.client.exception.ChunkNotFoundException;
import com.example.errorfreetext.dto.CheckTextRequest;
import com.example.errorfreetext.dto.SpellResult;
import com.example.errorfreetext.entity.TextChunk;
import com.example.errorfreetext.enums.Status;
import com.example.errorfreetext.repository.TextChunkRepository;
import com.example.errorfreetext.service.ChunkService;
import com.example.errorfreetext.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChunkServiceImpl implements ChunkService {
    private final YandexSpellerClient yandexSpellerClient;
    private final TaskService taskService;
    private final TextChunkRepository textChunkRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processChunk(UUID id) {
        TextChunk chunk = textChunkRepository.findById(id).orElseThrow(() -> new ChunkNotFoundException(id));
        log.info("Processing chunk {} for task {}", id, chunk.getTask().getId());
        chunk.setStatus(Status.IN_PROGRESS);
        textChunkRepository.save(chunk);
        CheckTextRequest checkTextRequest = new CheckTextRequest();
        checkTextRequest.setText(Collections.singletonList(chunk.getTextValue()));
        checkTextRequest.setLang(chunk.getLang());
        checkTextRequest.setOptions(defineOptionsParameter(chunk));
        try {
            List<List<SpellResult>> spellResults = yandexSpellerClient.checkTexts(checkTextRequest);

            String originalText = chunk.getTextValue();

            if (spellResults != null && !spellResults.isEmpty() && !spellResults.get(0).isEmpty()) {
                List<SpellResult> errors = spellResults.get(0);

                String correctedText = applyCorrections(originalText, errors, chunk.isIgnoreDigits(), chunk.isIgnoreUrls());
                chunk.setCorrectedValue(correctedText);
            } else {
                log.info("Chunk {} was checked, no errors was found.", chunk.getId());
                chunk.setCorrectedValue(originalText);
            }
            chunk.setStatus(Status.COMPLETED);
            textChunkRepository.save(chunk);

            taskService.updateTaskProgress(chunk.getTask().getId());
            log.info("Finished processing chunk {} for task {}", id, chunk.getTask().getId());
        } catch (Exception exception) {
            log.error("While processing yandex api request for chunk {}: {}", id, exception.getMessage());
            chunk.setStatus(Status.ERROR);
            textChunkRepository.save(chunk);
            taskService.failTask(chunk.getTask().getId(), exception);
            throw exception;
        }
    }

    private String applyCorrections(String text, List<SpellResult> errors, boolean ignoreDigits, boolean ignoreUrls) {
        StringBuilder stringBuilder = new StringBuilder(text);
        for (int i = errors.size() - 1; i >= 0; i--) {
            SpellResult error = errors.get(i);
            if (error.getS() == null || error.getS().isEmpty()) {
                continue;
            }

            int startIdx = error.getPos();
            int endIdx = startIdx + error.getLen();

            String replacement = error.getS().get(0);
            stringBuilder.replace(startIdx, endIdx, replacement);
        }
        return stringBuilder.toString();
    }

    private int defineOptionsParameter(TextChunk chunk) {
        int options = 0;

        if (chunk.isIgnoreDigits()) {
            options += 2;
        }

        if (chunk.isIgnoreUrls()) {
            options += 4;
        }

        return options;
    }

}
