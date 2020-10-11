package com.example.fileSharing.controller;

import com.example.fileSharing.dto.UserDto;
import com.example.fileSharing.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/auth/register")
  public ResponseEntity<String> registerNewUser(@RequestBody UserDto userDto) {
    try {
      authService.registerNewUserAccount(userDto);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Cannot save user!", HttpStatus.BAD_REQUEST);
    }
  }
}
