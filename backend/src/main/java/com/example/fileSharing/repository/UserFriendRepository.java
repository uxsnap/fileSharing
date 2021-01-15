package com.example.fileSharing.repository;

import com.example.fileSharing.entity.User;
import com.example.fileSharing.entity.UserFriend;
import com.example.fileSharing.helpers.FriendRequestStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface UserFriendRepository extends JpaRepository<UserFriend, UUID> {
  @Transactional
  @Modifying
  @Query(
    "delete from UserFriend uf where uf.user.id = ?1 and uf.id = ?2"
  )
  void deleteFriend(UUID currentUserId, UUID friendId);

  List<UserFriend> findAllByUserAndStatus(User userId, FriendRequestStatusEnum status);
  List<UserFriend> findAllByFriendProfileAndStatus(User friendProfile, FriendRequestStatusEnum status);
}
