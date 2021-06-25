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
import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("user")
public class UserController {
  private final UserService userService;
  private final FileService fileService;

  @GetMapping("/all")
  @BatchSize(size = 100)
  public ResponseEntity<ListDataDto<UserInfoDto>> getUsers(
    @PathParam("userName") String userName
  ) {
    ListDataDto<UserInfoDto> listDataDto = new ListDataDto<>();
    try {
      List<UserInfoDto> users = userService.getUsers(userName);
      listDataDto.setData(users);
      listDataDto.setMessage("OK");
      return ResponseEntity.ok(listDataDto);
    } catch (Exception e) {
      listDataDto.setErrors(Collections.singletonList(e.getMessage()));
      return ResponseEntity.badRequest().body(listDataDto);
    }
  }

  @GetMapping
  public ResponseEntity<UserInfoDto> getUser() throws Exception {
    UserInfoDto userInfoDto = new UserInfoDto();
    try {
      String userName = CurrentLoggedUser.getCurrentUser();
      UserDetails user = userService.loadUserByUsername(userName);
      userInfoDto.setUserName(userName);
      if (user != null) {
        userInfoDto.setMessage("OK");
        return ResponseEntity.ok(userInfoDto);
      }
      userInfoDto.setErrors(Collections.singletonList("No user with provided name"));
      return ResponseEntity.badRequest().body(userInfoDto);
    } catch (Exception e) {
      userInfoDto.setErrors(Collections.singletonList(e.getMessage()));
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
      avatarDto.setErrors(Collections.singletonList(e.getMessage()));
      avatarDto.setAvatar(null);
      return ResponseEntity.badRequest().body(avatarDto);
    }
  }

  @PostMapping("/avatar")
  public ResponseEntity<MessageDto> setUserAvatar(
    @RequestParam(name = "avatar") MultipartFile avatar
  ) {
    MessageDto messageDto = new MessageDto();
    try {
      fileService.uploadAvatar(avatar);
      messageDto.setMessage("OK");
      return ResponseEntity.ok(messageDto);
    } catch (Exception e) {
      messageDto.setErrors(Collections.singletonList(e.getMessage()));
      return ResponseEntity.badRequest().body(messageDto);
    }
  }
}
