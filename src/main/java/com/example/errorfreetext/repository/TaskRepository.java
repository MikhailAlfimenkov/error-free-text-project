package com.example.errorfreetext.repository;

import com.example.errorfreetext.entity.Task;
import com.example.errorfreetext.enums.Status;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByStatus(Status status);

    @Query("SELECT DISTINCT t FROM Task t " +
            "LEFT JOIN FETCH t.chunks " +
            "WHERE t.status = :status " +
            "ORDER BY t.createdAt ASC")
    List<Task> findTaskWithChunksByStatus(@Param("status") Status status, org.springframework.data.domain.Pageable pageable);


    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.chunks WHERE t.id = :id")
    Optional<Task> findByIdWithChunks(@Param("id") UUID id);
}


