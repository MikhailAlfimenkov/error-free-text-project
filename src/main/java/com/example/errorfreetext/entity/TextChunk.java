package com.example.errorfreetext.entity;

import com.example.errorfreetext.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "text_chunks")
@NoArgsConstructor
public class TextChunk {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "text_value", nullable = false, columnDefinition = "TEXT")
    private String textValue;

    @Column(name = "corrected_value", columnDefinition = "TEXT")
    private String correctedValue;

    @Column(nullable = false, length = 10)
    private String lang;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private Status status;

    @Column(name = "chunk_order", nullable = false)
    private Integer chunkOrder;

    @Column(name = "ignore_digits")
    private boolean ignoreDigits;

    @Column(name = "ignore_urls")
    private boolean ignoreUrls;
}
