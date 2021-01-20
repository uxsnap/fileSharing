package com.example.fileSharing.controller;

import com.example.fileSharing.dto.JwtResponse;
import com.example.fileSharing.dto.MessageDto;
import com.example.fileSharing.dto.UserAndPassAuthDto;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.helpers.CurrentLoggedUser;
import com.example.fileSharing.helpers.ErrorMessageUnwrapper;
import com.example.fileSharing.repository.UserRepository;
import com.example.fileSharing.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> loginUser(
    @Valid @RequestBody UserAndPassAuthDto userDto, Errors errors) {
    JwtResponse jwtResponse = new JwtResponse();
    if (errors.hasErrors()) {
      jwtResponse.setErrors(ErrorMessageUnwrapper.errors(errors));
      return ResponseEntity.badRequest().body(jwtResponse);
    }
    try {
      jwtResponse = authService.loginUser(userDto);
      return ResponseEntity.ok(jwtResponse);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(jwtResponse);
    }
  }

  @PostMapping("/register")
  public ResponseEntity<MessageDto> registerNewUser(@Valid @RequestBody UserAndPassAuthDto userDto, Errors errors) {
    if (errors.hasErrors()) {
      MessageDto messageDto = new MessageDto();
      messageDto.setErrors(ErrorMessageUnwrapper.errors(errors));
      return ResponseEntity.badRequest().body(messageDto);
    }
    MessageDto messageDto = new MessageDto();
    try {
      authService.registerNewUserAccount(userDto);
      return ResponseEntity.ok(messageDto);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(messageDto);
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<MessageDto> logoutUser() {
    authService.logoutUser();
    return ResponseEntity.ok(new MessageDto("OK"));
  }
}
