package com.example.fileSharing.repository;

import com.example.fileSharing.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {
  PasswordResetToken findByToken(UUID token);
}
