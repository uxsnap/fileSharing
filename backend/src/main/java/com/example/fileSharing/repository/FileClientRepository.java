package com.example.fileSharing.repository;

import com.example.fileSharing.entity.FileClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileClientRepository extends JpaRepository<FileClient, UUID> {
}
