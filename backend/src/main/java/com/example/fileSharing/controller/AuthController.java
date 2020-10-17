package com.example.fileSharing.controller;

import com.example.fileSharing.dto.JsonResponse;
import com.example.fileSharing.dto.UserDto;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.repository.UserRepository;
import com.example.fileSharing.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
  private final UserRepository userRepository;
  private final AuthService authService;

  @PostMapping("/register")
  public JsonResponse registerNewUser(@RequestBody UserDto userDto) {
    return authService.registerNewUserAccount(userDto);
  }

  @GetMapping("/users")
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/users/{userName}")
  public User getUser(@PathVariable(name = "userName") String userName) {
    return userRepository.findByUsername(userName);
  }
}
