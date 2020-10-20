package com.example.fileSharing.controller;

import com.example.fileSharing.dto.FriendDto;
import com.example.fileSharing.dto.JsonResponse;
import com.example.fileSharing.service.FriendService;
import com.example.fileSharing.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final FriendService friendService;

  @PostMapping("/add/friend")
  public JsonResponse addFriend(
    @RequestBody List<FriendDto> friendDto
  ) {
    return friendService.addFriend(friendDto);
  }
}
