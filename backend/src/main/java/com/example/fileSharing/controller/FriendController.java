package com.example.fileSharing.controller;

import com.example.fileSharing.dto.*;
import com.example.fileSharing.entity.UserFriend;
import com.example.fileSharing.service.FileService;
import com.example.fileSharing.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @RequestBody UserIdsDto users
  ) {
    UserIdsDto friends = new UserIdsDto();
    try {
      List<UserFriend> addedUsers = friendService.sendFriendRequests(users);
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

  @GetMapping("/{potentialFriendId}/decline")
  public ResponseEntity<UUID> declineFriendRequests(
    @PathVariable(name = "potentialFriendId") UUID potentialFriendId
  ) {
    try {
      friendService.declineFriendRequest(potentialFriendId);
      return ResponseEntity.ok(potentialFriendId);
    } catch (Exception e) {
      return new ResponseEntity<>(potentialFriendId, HttpStatus.INTERNAL_SERVER_ERROR);
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
      return new ResponseEntity<>(messageDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{friendId}/delete")
  public ResponseEntity<MessageDto> deleteFriend(
    @PathVariable(name = "friendId") UUID friendId
  ) {
    try {
      friendService.deleteFriend(friendId);
      return new ResponseEntity<>(new MessageDto("OK"), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new MessageDto("Cannot delete this users"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/all")
  public ResponseEntity<UserInfoListDto> getAllFriends() {
    UserInfoListDto userInfoListDto = new UserInfoListDto();
    try {
      List<UserInfoDto> users = friendService.getFriends();
      userInfoListDto.setUsers(users);
      userInfoListDto.setMessage("OK");
      return new ResponseEntity<>(userInfoListDto, HttpStatus.OK);
    } catch (Exception e) {
      userInfoListDto.setUsers(new ArrayList<>());
      userInfoListDto.setMessage("Cannot obtain user friends");
      return new ResponseEntity<>(
              userInfoListDto,
              HttpStatus.INTERNAL_SERVER_ERROR
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
      return new ResponseEntity<>(userInfoListDto, HttpStatus.OK);
    } catch (Exception e) {
      userInfoListDto.setUsers(new ArrayList<>());
      userInfoListDto.setMessage("Cannot obtain friend request users");
      return new ResponseEntity<>(
              userInfoListDto,
              HttpStatus.INTERNAL_SERVER_ERROR
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
      return ResponseEntity.ok(friendsFiles);
    } catch (Exception e) {
      return new ResponseEntity<>(
          friendsFiles,
          HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }
}
