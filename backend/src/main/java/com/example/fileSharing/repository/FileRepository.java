package com.example.fileSharing.repository;

import com.example.fileSharing.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
  List<File> findAllByUserId(UUID userId);
  @Transactional
  @Modifying
  @Query("delete from File where id = ?1")
  void deleteFileById(UUID fileId);

  @Transactional
  @Modifying
  @Query("update File set originalName = ?2 where id = ?1")
  void editFileName(UUID fileId, String fileName);
}
