package com.example.errorfreetext.repository;

import com.example.errorfreetext.entity.TextChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TextChunkRepository extends JpaRepository<TextChunk, UUID> {

}


