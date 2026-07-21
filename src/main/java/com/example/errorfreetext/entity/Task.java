package com.example.errorfreetext.entity;

import com.example.errorfreetext.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;


@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private Status status;


    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("chunkOrder ASC")
    private List<TextChunk> chunks = new ArrayList<>();

    @Column(name = "completed_chunks_count")
    private int completedChunksCount;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

}


