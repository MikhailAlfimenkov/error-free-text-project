package com.example.errorfreetext.mapper;

import com.example.errorfreetext.dto.TaskResponse;
import com.example.errorfreetext.dto.TextChunkResponse;
import com.example.errorfreetext.entity.Task;
import com.example.errorfreetext.entity.TextChunk;
import com.example.errorfreetext.enums.Status;
import org.mapstruct.*;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    TaskResponse toResponse(Task task);

    TextChunkResponse toResponse(TextChunk textChunk);

    List<TextChunkResponse> toResponseList(List<TextChunk> textChunks);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "text", ignore = true)
    TaskResponse toCompletedResponse(Task task);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "text", ignore = true)
    @Mapping(source = "errorMessage", target = "errorMessage")
    TaskResponse toErrorResponse(Task task);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "text", ignore = true)
    @Mapping(target = "errorMessage", ignore = true)
    TaskResponse toTaskResponse(Task task);

    @AfterMapping
    default void setText(Task task, @MappingTarget TaskResponse response) {
        if (Status.COMPLETED == task.getStatus()) {
            if (task.getChunks() != null) {
                String fullText = task.getChunks().stream()
                        .sorted(Comparator.comparingInt(TextChunk::getChunkOrder))
                        .map(TextChunk::getTextValue)
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(" "));
                response.setText(fullText);
            }
        }
    }
}
