package com.example.fileSharing.controller;

import com.example.fileSharing.dto.*;
import com.example.fileSharing.service.FriendService;
import com.example.fileSharing.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final FriendService friendService;
  private final UserService userService;

  @GetMapping("/{userName}")
  public ResponseEntity<Object> getUser(
    @PathVariable("userName") String userName
  ) {
    try {
      UserDetails user = userService.loadUserByUsername(userName);
      if (user != null)
        return new ResponseEntity<Object>(new UserInfoDto(userName), HttpStatus.OK);
      return new ResponseEntity<>(new MessageDto("No user with provided name"), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<Object>(new MessageDto("Error while fetching user data"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/add/friend")
  public JsonResponse addFriend(
    @RequestBody List<FriendDto> friendDto
  ) {
    return friendService.addFriend(friendDto);
  }
}
