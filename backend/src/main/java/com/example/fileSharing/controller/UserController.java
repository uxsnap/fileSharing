package com.example.fileSharing.controller;

import com.example.fileSharing.dto.*;
import com.example.fileSharing.service.FileService;
import com.example.fileSharing.service.FriendService;
import com.example.fileSharing.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final FriendService friendService;
  private final UserService userService;
  private final FileService fileService;

  @GetMapping("/{userName}")
  public ResponseEntity<Object> getUser(
    @PathVariable("userName") String userName
  ) {
    try {
      UserDetails user = userService.loadUserByUsername(userName);
      if (user != null)
        return new ResponseEntity<>(new UserInfoDto(userName), HttpStatus.OK);
      return new ResponseEntity<>(new MessageDto("No user with provided name"), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new MessageDto("Error while fetching user data"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/add/friend")
  public JsonResponse addFriend(
    @RequestBody List<FriendDto> friendDto
  ) {
    return friendService.addFriend(friendDto);
  }

  @GetMapping("/{userName}/avatar")
  public ResponseEntity<AvatarDto> getUserAvatar(
    @PathVariable("userName") String userName
  ) {
    try {
      String avatar = userService.getAvatar(userName);
      AvatarDto avatarDto = new AvatarDto();
      avatarDto.setAvatar(avatar);
      avatarDto.setMessage("OK");
      return new ResponseEntity<>(avatarDto, HttpStatus.OK);
    } catch (Exception e) {
      AvatarDto avatarDto = new AvatarDto();
      avatarDto.setMessage("Problem with fetching avatar");
      avatarDto.setAvatar(null);
      return new ResponseEntity<>(avatarDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/{userName}/avatar")
  public ResponseEntity<MessageDto> setUserAvatar(
    @PathVariable("userName") String userName,
    @RequestParam(name = "avatar") MultipartFile avatar
  ) {
    try {
      fileService.uploadAvatar(userName, avatar);
      return new ResponseEntity<>(new MessageDto("OK"), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new MessageDto("Problem with uploading the avatar"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
