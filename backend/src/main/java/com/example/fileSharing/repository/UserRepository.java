package com.example.fileSharing.repository;

import com.example.fileSharing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
//  User findByEmail(String email);
  User findByUsername(String username);
}
