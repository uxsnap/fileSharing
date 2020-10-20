package com.example.fileSharing.service;

import com.example.fileSharing.dto.FriendDto;
import com.example.fileSharing.dto.JsonResponse;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.entity.UserFriend;
import com.example.fileSharing.helpers.CurrentLoggedUser;
import com.example.fileSharing.repository.UserFriendRepository;
import com.example.fileSharing.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendService {
  private final UserFriendRepository userFriendRepository;
  private final UserRepository userRepository;

  public JsonResponse addFriend(List<FriendDto> friendsDto) {
    try {
      String currentUser = CurrentLoggedUser.getCurrentUser();
      User user = userRepository.findByUsername(currentUser);
      List<String> currentUserFriends = user
        .getUserFriends()
        .stream()
        .map(UserFriend::getUserName).collect(Collectors.toList());
      List<UserFriend> userFriends = new ArrayList<>();
      for (FriendDto friendDto: friendsDto) {
        String name = friendDto.getName();
        if (currentUserFriends.contains(name)) {
          continue;
        }
        User listUser = userRepository.findByUsername(name);
        if (listUser == null) {
          return new JsonResponse(
            "Cannot find user with this name",
            HttpStatus.BAD_REQUEST.value()
          );
        }
        userFriends.add(
          new UserFriend(name, user)
        );
      }

      for (UserFriend userFriend: userFriends) {
        userFriendRepository.save(userFriend);
      }
      return JsonResponse.ok();
    } catch (Exception e) {
      return JsonResponse.wrong();
    }
  }
}
