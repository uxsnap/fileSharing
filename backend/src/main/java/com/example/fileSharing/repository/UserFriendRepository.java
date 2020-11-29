package com.example.fileSharing.repository;

import com.example.fileSharing.entity.User;
import com.example.fileSharing.entity.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserFriendRepository extends JpaRepository<UserFriend, UUID> {
  List<UserFriend> findAllByUser(User user);
}
