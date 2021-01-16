package com.example.fileSharing.service;

import com.example.fileSharing.dto.UserIdsDto;
import com.example.fileSharing.dto.UserInfoDto;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.entity.UserFriend;
import com.example.fileSharing.helpers.CurrentLoggedUser;
import com.example.fileSharing.helpers.FriendRequestStatusEnum;
import com.example.fileSharing.helpers.Func;
import com.example.fileSharing.repository.UserFriendRepository;
import com.example.fileSharing.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendService {
  private final UserFriendRepository userFriendRepository;
  private final UserRepository userRepository;

  private UUID getCurrentUserId() {
    String currentUser = CurrentLoggedUser.getCurrentUser();
    return userRepository.findByUsername(currentUser).getId();
  }

  public List<UserFriend> sendFriendRequests(UserIdsDto userIdsDto) {
    List<UUID> users = userIdsDto.getUsers();
    UUID curUserId = getCurrentUserId();
    User user = userRepository.findById(curUserId).orElse(null);
    if (user == null) throw new NullPointerException("Cannot find user");
    List<UUID> currentUserFriends = user
      .getUserFriends()
      .stream()
      .map(UserFriend::getId).collect(Collectors.toList());
    List<UserFriend> userFriendRequests = new ArrayList<>();
    for (UUID id: users) {
      if (currentUserFriends.contains(id)) {
        continue;
      }
      User listUser = userRepository.findById(id).orElseThrow(null);
      if (listUser == null) throw new NullPointerException("Cannot find user with this name");
      userFriendRequests.add(
        new UserFriend(user, listUser, FriendRequestStatusEnum.PENDING)
      );
    }
    for (UserFriend friendRequest: userFriendRequests) {
      userFriendRepository.save(friendRequest);
    }

    return userFriendRequests;
  }

  public void addFriend(UUID potentialFriendId) {
    UUID curUserId = getCurrentUserId();
    User user = userRepository.findById(curUserId).orElse(null);
    User friend = userRepository.findById(potentialFriendId).orElse(null);
    if (user == null || friend == null) throw new NullPointerException("Cannot find user");

    UserFriend userFriend = userFriendRepository
      .findByUserAndFriendProfileAndStatus(
        friend,
        user,
        FriendRequestStatusEnum.PENDING
    );

    if (userFriend == null) throw new NullPointerException("Cannot find friend");

    userFriend.setStatus(FriendRequestStatusEnum.ACCEPTED);
    userFriendRepository.save(
      userFriend
    );
    userFriendRepository.save(
      new UserFriend(
        userFriend.getFriendProfile(),
        userFriend.getUser(),
        FriendRequestStatusEnum.ACCEPTED
      )
    );
  }

  public void declineFriendRequest(UUID potentialFriendId) {
    userFriendRepository.deleteFriend(getCurrentUserId(), potentialFriendId);
  }

  public void deleteFriend(UUID friendId) {
    userFriendRepository.deleteFriend(getCurrentUserId(), friendId);
  }

  private List<UserInfoDto> handleFriends(
    List<UserFriend> userFriends,
    Func<UserFriend, UserInfoDto> func
  ) {
    return userFriends
      .stream()
      .map(func::calculate)
      .collect(Collectors.toList());
  }

  public List<UserInfoDto> getFriends() {
    String currentUser = CurrentLoggedUser.getCurrentUser();
    User user = userRepository.findByUsername(currentUser);
    return handleFriends(
      userFriendRepository
        .findAllByUserAndStatus(user, FriendRequestStatusEnum.ACCEPTED),
        userFriend -> new UserInfoDto(
          userFriend.getFriendProfile().getId(),
          userFriend.getFriendProfile().getUsername())
    );
  }

  public List<UserInfoDto> getPotentialFriends() {
    String currentUser = CurrentLoggedUser.getCurrentUser();
    User user = userRepository.findByUsername(currentUser);
    return handleFriends(
      userFriendRepository
        .findAllByFriendProfileAndStatus(user, FriendRequestStatusEnum.PENDING),
      userFriend -> new UserInfoDto(
        userFriend.getUser().getId(),
        userFriend.getUser().getUsername())
    );
  }
}
