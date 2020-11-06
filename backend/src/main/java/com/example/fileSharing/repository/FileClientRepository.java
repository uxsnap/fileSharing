package com.example.fileSharing.repository;

import com.example.fileSharing.entity.FileClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface FileClientRepository extends JpaRepository<FileClient, UUID> {
  @Transactional
  @Query("delete from FileClient where file.id = ?1")
  @Modifying
  void deleteByFileId(UUID fileId);
}
