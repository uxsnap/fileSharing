package com.example.fileSharing.service;

import com.example.fileSharing.dto.*;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.helpers.ConstantClass;
import com.example.fileSharing.helpers.UserAlreadyExistAuthenticationException;
import com.example.fileSharing.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public JwtResponse loginUser(UserAndPassAuthDto userDto) {
    JwtResponse jwtResponse = new JwtResponse();
    try {
      String username = userDto.getUserName();

      Authentication authentication = new UsernamePasswordAuthenticationToken(
        username,
        userDto.getPassword()
      );
      authenticationManager.authenticate(authentication);

      String token = Jwts.builder()
        .setSubject(authentication.getName())
        .claim("authorities", authentication.getAuthorities())
        .setIssuedAt(new Date())
        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
        .signWith(Keys.hmacShaKeyFor(ConstantClass.SECRET_KEY.getBytes()))
        .compact();

      jwtResponse.setToken(token);
      jwtResponse.setMessage("OK");
      jwtResponse.setUsername(username);
      return jwtResponse;
    } catch (Exception e) {
      jwtResponse.setMessage("Cannot login!");
      return jwtResponse;
    }
  }

  public void registerNewUserAccount(UserAndPassAuthDto accountDto)  {
    User found = userRepository.findByUsername(accountDto.getUserName());
    if (found != null) {
      throw new UserAlreadyExistAuthenticationException("User already created!");
    }
    try {
      User user = new User();
      user.setUsername(accountDto.getUserName());
      user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
      userRepository.save(user);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void logoutUser() {
  }
}
