package com.example.fileSharing.controller;

import com.example.fileSharing.dto.FriendsDto;
import com.example.fileSharing.dto.MessageDto;
import com.example.fileSharing.dto.UserInfoDto;
import com.example.fileSharing.dto.UserInfoListDto;
import com.example.fileSharing.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/friend")
public class FriendController {
  private FriendService friendService;

  @PostMapping("/add")
  public ResponseEntity<MessageDto> addFriend(
          @RequestBody FriendsDto friendsDto
  ) {
    try {
      friendService.addFriend(friendsDto);
      return new ResponseEntity<>(new MessageDto("OK"), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new MessageDto("Cannot add friend"), HttpStatus.INTERNAL_SERVER_ERROR);
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
