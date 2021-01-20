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
    "delete from UserFriend uf " +
    "where (uf.user.id = ?1 and uf.friendProfile.id = ?2) " +
    "or (uf.user.id = ?2 and uf.friendProfile.id = ?1)"
  )
  void deleteFriend(UUID currentUserId, UUID friendId);

  UserFriend findByUserAndFriendProfileAndStatus(
    User user,
    User friendProfile,
    FriendRequestStatusEnum status
  );
  List<UserFriend> findAllByUserAndStatus(User user, FriendRequestStatusEnum status);
  List<UserFriend> findAllByFriendProfileAndStatus(User friendProfile, FriendRequestStatusEnum status);
}
