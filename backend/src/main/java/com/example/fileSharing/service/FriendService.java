package com.example.fileSharing.service;

import com.example.fileSharing.dto.FriendsDto;
import com.example.fileSharing.dto.UserInfoDto;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.entity.UserFriend;
import com.example.fileSharing.helpers.CurrentLoggedUser;
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

  public void addFriend(FriendsDto friendsDto) {
    List<UUID> users = friendsDto.getUsers();
    String currentUser = CurrentLoggedUser.getCurrentUser();
    User user = userRepository.findByUsername(currentUser);
    List<UUID> currentUserFriends = user
      .getUserFriends()
      .stream()
      .map(UserFriend::getFriendId).collect(Collectors.toList());
    List<UserFriend> userFriends = new ArrayList<>();
    for (UUID id: users) {
      if (currentUserFriends.contains(id)) {
        continue;
      }
      User listUser = userRepository.findById(id).orElse(null);
      if (listUser == null) throw new NullPointerException("Cannot find user with this name");
      userFriends.add(
        new UserFriend(id, user)
      );
    }
    for (UserFriend userFriend: userFriends) {
      userFriendRepository.save(userFriend);
    }
  }

  public List<UserInfoDto> getFriends() {
    String currentUser = CurrentLoggedUser.getCurrentUser();
    User user = userRepository.findByUsername(currentUser);
    return userFriendRepository
      .findAllByUser(user)
      .stream().map(userFriend -> new UserInfoDto(userFriend.getId(), userFriend.getUser().getUsername()))
      .collect(Collectors.toList());
  }
}
