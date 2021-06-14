package com.example.fileSharing.controller;

import com.example.fileSharing.dto.*;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.helpers.ErrorMessageUnwrapper;
import com.example.fileSharing.service.AuthService;
import com.example.fileSharing.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> loginUser(
    @Valid @RequestBody LoginDto userDto, Errors errors) {
    JwtResponse jwtResponse = new JwtResponse();
    if (errors.hasErrors()) {
      jwtResponse.setErrors(ErrorMessageUnwrapper.errors(errors));
      return ResponseEntity.badRequest().body(jwtResponse);
    }
    jwtResponse = authService.loginUser(userDto);
    if (jwtResponse.getErrors().isEmpty())
      return ResponseEntity.ok(jwtResponse);
    return ResponseEntity.badRequest().body(jwtResponse);
  }

  @PostMapping("/register")
  public ResponseEntity<MessageDto> registerNewUser(@Valid @RequestBody RegisterDto userDto, Errors errors) {
    if (errors.hasErrors()) {
      MessageDto messageDto = new MessageDto();
      messageDto.setErrors(ErrorMessageUnwrapper.errors(errors));
      return ResponseEntity.badRequest().body(messageDto);
    }

    MessageDto messageDto = new MessageDto();
    try {
      messageDto.setMessage("New user has been registered");
      authService.registerNewUserAccount(userDto);
      return ResponseEntity.ok(messageDto);
    } catch (Exception e) {
      messageDto.setErrors(Collections.singletonList(e.getMessage()));
      return ResponseEntity.badRequest().body(messageDto);
    }
  }

  /* Future functionality */
  @PostMapping("/resetPassword")
  public ResponseEntity<MessageDto> resetPassword(@Valid @RequestBody EmailDto emailDto, Errors errors) {
    if (errors.hasErrors()) {
      MessageDto messageDto = new MessageDto();
      messageDto.setErrors(ErrorMessageUnwrapper.errors(errors));
      return ResponseEntity.badRequest().body(messageDto);
    }

    User user = userService.findByEmail(emailDto.getEmail());
    MessageDto messageDto = new MessageDto();
    if (user == null) {
      messageDto.setErrors(Collections.singletonList("Cannot find user with this email"));
      return ResponseEntity.badRequest().body(messageDto);
    }

    try {
//      authService.resetUserPassword(user);
      messageDto.setMessage("Link to reset your password has been sent to your email");
      return ResponseEntity.ok(messageDto);
    } catch (Exception e) {
      messageDto.setErrors(Collections.singletonList(e.getMessage()));
      return ResponseEntity.badRequest().body(messageDto);
    }
  }

//
//  @PostMapping("/saveNewPassword")
//  public ResponseEntity<MessageDto> saveNewPassword(@Valid PasswordDto passwordDto) {
//
//  }

  @PostMapping("/logout")
  public ResponseEntity<MessageDto> logoutUser() {
    authService.logoutUser();
    return ResponseEntity.ok(new MessageDto("OK"));
  }
}
