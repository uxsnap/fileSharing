package com.example.fileSharing.controller;

import com.example.fileSharing.dto.MessageDto;
import com.example.fileSharing.dto.UserAndPassAuthDto;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.helpers.CurrentLoggedUser;
import com.example.fileSharing.repository.UserRepository;
import com.example.fileSharing.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
  private final UserRepository userRepository;
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity loginUser(@RequestBody UserAndPassAuthDto userDto) {
    return authService.loginUser(userDto);
  }

  @PostMapping("/register")
  public ResponseEntity registerNewUser(@RequestBody UserAndPassAuthDto userDto) {
    return authService.registerNewUserAccount(userDto);
  }

  @GetMapping("/users")
  public User getUser() {
    String userName = CurrentLoggedUser.getCurrentUser();
    return userRepository.findByUsername(userName);
  }

  @PostMapping("/logout")
  public ResponseEntity<MessageDto> logoutUser() {
    return authService.logoutUser();
  }

  @GetMapping("/context")
  public ResponseEntity<MessageDto> checkContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return new ResponseEntity<>(new MessageDto(authentication.getName()), HttpStatus.OK);
  }
}
