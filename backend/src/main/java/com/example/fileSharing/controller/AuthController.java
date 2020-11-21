package com.example.fileSharing.controller;

import com.example.fileSharing.dto.MessageDto;
import com.example.fileSharing.dto.UserAndPassAuthDto;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.repository.UserRepository;
import com.example.fileSharing.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import sun.plugin2.message.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

  @GetMapping("/logout")
  public ResponseEntity<MessageDto> logoutUser(HttpServletRequest request) {
    return new ResponseEntity<>(new MessageDto("OK"), HttpStatus.OK);
  }

  @GetMapping("/context")
  public ResponseEntity<MessageDto> checkContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return new ResponseEntity<>(new MessageDto(authentication.getName()), HttpStatus.OK);
  }
}
