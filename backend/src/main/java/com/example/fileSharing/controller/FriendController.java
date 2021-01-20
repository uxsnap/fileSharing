package com.example.fileSharing.controller;

import com.example.fileSharing.dto.*;
import com.example.fileSharing.entity.UserFriend;
import com.example.fileSharing.helpers.ErrorMessageUnwrapper;
import com.example.fileSharing.service.FileService;
import com.example.fileSharing.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/friend")
public class FriendController {
  private final FriendService friendService;
  private final FileService fileService;

  @PostMapping("/sendRequest")
  public ResponseEntity<UserIdsDto> sendFriendRequests(
    @Valid  @RequestBody UserIdsDto users,
    Errors errors
  ) {
    if (errors.hasErrors()) {
      UserIdsDto userIdsDto = new UserIdsDto();
      userIdsDto.setErrors(ErrorMessageUnwrapper.errors(errors));
      return ResponseEntity.badRequest().body(userIdsDto);
    }
    UserIdsDto friends = new UserIdsDto();
    try {
      List<UserFriend> addedUsers = friendService.sendFriendRequests(users);
      friends.setUsers(addedUsers.stream().map(
        user -> user.getFriendProfile().getId()).collect(Collectors.toList()));
      friends.setMessage("OK");
      return ResponseEntity.ok(friends);
    } catch (Exception e) {
      friends.setUsers(new ArrayList<>());
      friends.setMessage("Cannot add a friend");
      return ResponseEntity.badRequest().body(friends);
    }
  }

  @GetMapping("/{potentialFriendId}/decline")
  public ResponseEntity<UserIdDto> declineFriendRequests(
    @PathVariable(name = "potentialFriendId") UUID potentialFriendId
  ) {
    UserIdDto userIdDto = new UserIdDto();
    try {
      friendService.declineFriendRequest(potentialFriendId);
      userIdDto.setMessage("OK");
      userIdDto.setId(potentialFriendId);
      return ResponseEntity.ok(userIdDto);
    } catch (Exception e) {
      userIdDto.setMessage("Cannot decline friend request");
      return ResponseEntity.badRequest().body(userIdDto);
    }
  }

  @GetMapping("/{potentialFriendId}/add")
  public ResponseEntity<MessageDto> acceptFriendRequest(
      @PathVariable(name = "potentialFriendId") UUID potentialFriendId
  ) {
    MessageDto messageDto = new MessageDto();
    try {
      messageDto.setMessage("OK");
      friendService.addFriend(potentialFriendId);
      return ResponseEntity.ok(messageDto);
    } catch (Exception e) {
      messageDto.setMessage("Error while accepting friend request");
      return ResponseEntity.badRequest().body(messageDto);
    }
  }

  @DeleteMapping("/{friendId}/delete")
  public ResponseEntity<MessageDto> deleteFriend(
    @PathVariable(name = "friendId") UUID friendId
  ) {
    try {
      friendService.deleteFriend(friendId);
      return ResponseEntity.ok(new MessageDto("OK"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageDto("Cannot delete this user"));
    }
  }

  @GetMapping("/all")
  public ResponseEntity<UserInfoListDto> getAllFriends() {
    UserInfoListDto userInfoListDto = new UserInfoListDto();
    try {
      List<UserInfoDto> users = friendService.getFriends();
      userInfoListDto.setUsers(users);
      userInfoListDto.setMessage("OK");
      return ResponseEntity.ok(userInfoListDto);
    } catch (Exception e) {
      userInfoListDto.setUsers(new ArrayList<>());
      userInfoListDto.setMessage("Cannot obtain user friends");
      return ResponseEntity.badRequest().body(
        userInfoListDto
      );
    }
  }

  @GetMapping("/requests")
  public ResponseEntity<UserInfoListDto> getAllPotentialFriends() {
    UserInfoListDto userInfoListDto = new UserInfoListDto();
    try {
      List<UserInfoDto> users = friendService.getPotentialFriends();
      userInfoListDto.setUsers(users);
      userInfoListDto.setMessage("OK");
      return ResponseEntity.ok(userInfoListDto);
    } catch (Exception e) {
      userInfoListDto.setUsers(new ArrayList<>());
      userInfoListDto.setMessage("Cannot obtain friend request users");
      return ResponseEntity.badRequest().body(
        userInfoListDto
      );
    }
  }

  @GetMapping("/{friendId}/files")
  public ResponseEntity<FilesDto<FileShortInfoDto>> getFriendFiles(
      @PathVariable(name = "friendId") UUID friendId
  ) {
    FilesDto<FileShortInfoDto> friendsFiles = new FilesDto<>();
    try {
      friendsFiles.setFiles(fileService.getFriendFiles(friendId));
      friendsFiles.setMessage("OK");
      return ResponseEntity.ok(friendsFiles);
    } catch (Exception e) {
      friendsFiles.setMessage("Cannot obtain friend files");
      return ResponseEntity.badRequest().body(
        friendsFiles
      );
    }
  }
}
