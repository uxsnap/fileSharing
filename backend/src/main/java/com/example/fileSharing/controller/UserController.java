package com.example.fileSharing.controller;

import com.example.fileSharing.dto.*;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.helpers.CurrentLoggedUser;
import com.example.fileSharing.service.FileService;
import com.example.fileSharing.service.FriendService;
import com.example.fileSharing.service.UserService;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserService userService;
  private final FileService fileService;

  @GetMapping("/all")
  @BatchSize(size = 100)
  public ResponseEntity<UsersDto> getUsers(
    @PathParam("userName") String userName
  ) {
    UsersDto usersDto = new UsersDto();
    try {
      List<UserInfoDto> users = userService.getUsers(userName);
      usersDto.setUsers(users);
      usersDto.setMessage("OK");
      return ResponseEntity.ok(usersDto);
    } catch (Exception e) {
      usersDto.setMessage("Error while fetching users");
      return ResponseEntity.badRequest().body(usersDto);
    }
  }

  @GetMapping
  public ResponseEntity<UserInfoDto> getUser() {
    UserInfoDto userInfoDto = new UserInfoDto();
    try {
      String userName = CurrentLoggedUser.getCurrentUser();
      UserDetails user = userService.loadUserByUsername(userName);
      userInfoDto.setUserName(userName);
      if (user != null) {
        userInfoDto.setMessage("OK");
        return ResponseEntity.ok(userInfoDto);
      }
      userInfoDto.setMessage("No user with provided name");
      return ResponseEntity.badRequest().body(userInfoDto);
    } catch (Exception e) {
      userInfoDto.setMessage("Error while fetching user data");
      return ResponseEntity.badRequest().body(userInfoDto);
    }
  }

  @GetMapping("/avatar")
  public ResponseEntity<AvatarDto> getUserAvatar(
  ) {
    try {
      String userName = CurrentLoggedUser.getCurrentUser();
      String avatar = userService.getAvatar(userName);
      AvatarDto avatarDto = new AvatarDto();
      avatarDto.setAvatar(avatar);
      avatarDto.setMessage("OK");
      return ResponseEntity.ok(avatarDto);
    } catch (Exception e) {
      AvatarDto avatarDto = new AvatarDto();
      avatarDto.setMessage("Problem with fetching avatar");
      avatarDto.setAvatar(null);
      return ResponseEntity.badRequest().body(avatarDto);
    }
  }

  @PostMapping("/avatar")
  public ResponseEntity<MessageDto> setUserAvatar(
    @RequestParam(name = "avatar") MultipartFile avatar
  ) {
    try {
      fileService.uploadAvatar(avatar);
      return ResponseEntity.ok(new MessageDto("OK"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(
        new MessageDto("Problem with uploading the avatar")
      );
    }
  }
}
