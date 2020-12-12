package com.example.fileSharing.controller;

import com.example.fileSharing.dto.*;
import com.example.fileSharing.entity.UserFriend;
import com.example.fileSharing.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/friend")
public class FriendController {
  private FriendService friendService;

  @PostMapping("/add")
  public ResponseEntity<FriendsDto> addFriend(
    @RequestBody FriendsDto users
  ) {
    FriendsDto friends = new FriendsDto();
    try {
      List<UserFriend> addedUsers = friendService.addFriend(users);
      friends.setUsers(addedUsers.stream().map(
        user -> user.getFriendProfile().getId()).collect(Collectors.toList()));
      friends.setMessage("OK");
      return new ResponseEntity<>(friends, HttpStatus.OK);
    } catch (Exception e) {
      friends.setUsers(new ArrayList<>());
      friends.setMessage("Cannot add friend");
      return new ResponseEntity<>(friends, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/all")
  public ResponseEntity<UserInfoListDto> getAllFriends() {
    UserInfoListDto userInfoListDto = new UserInfoListDto();
    try {
      List<UserInfoDto> users = friendService.getFriends();
      userInfoListDto.setUsers(users);
      userInfoListDto.setMessage("OK");
      return new ResponseEntity<UserInfoListDto>(userInfoListDto, HttpStatus.OK);
    } catch (Exception e) {
      userInfoListDto.setUsers(new ArrayList<>());
      userInfoListDto.setMessage("Cannot obtain user friends");
      return new ResponseEntity<UserInfoListDto>(
              userInfoListDto,
              HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }
}
