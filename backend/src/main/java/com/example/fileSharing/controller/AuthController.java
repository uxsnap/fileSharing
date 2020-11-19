package com.example.fileSharing.controller;

import com.example.fileSharing.dto.UserAndPassAuthDto;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.repository.UserRepository;
import com.example.fileSharing.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping("/users/{userName}")
  public User getUser(@PathVariable(name = "userName") String userName) {
    return userRepository.findByUsername(userName);
  }
}
