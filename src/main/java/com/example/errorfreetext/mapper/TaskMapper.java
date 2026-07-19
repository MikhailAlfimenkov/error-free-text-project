package com.example.errorfreetext.mapper;

import com.example.errorfreetext.dto.TaskResponse;
import com.example.errorfreetext.dto.TextChunkResponse;
import com.example.errorfreetext.entity.Task;
import com.example.errorfreetext.entity.TextChunk;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    TaskResponse toResponse(Task task);

    TextChunkResponse toResponse(TextChunk textChunk);

    List<TextChunkResponse> toResponseList(List<TextChunk> textChunks);
}
