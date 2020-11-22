package com.example.fileSharing.service;

import com.example.fileSharing.dto.*;
import com.example.fileSharing.entity.Role;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.helpers.ConstantClass;
import com.example.fileSharing.repository.RoleRepository;
import com.example.fileSharing.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public ResponseEntity<Object> loginUser(UserAndPassAuthDto userDto) {
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

      return new ResponseEntity<>(new JwtResponse(token, username), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new MessageDto("Cannot login!"), HttpStatus.BAD_REQUEST);
    }
  }

  public ResponseEntity<MessageDto> registerNewUserAccount(UserAndPassAuthDto accountDto)  {
    JsonResponse response = new JsonResponse();
    User found = userRepository.findByUsername(accountDto.getUserName());
    if (found != null) {
      return new ResponseEntity<>(new MessageDto("User already created"), HttpStatus.BAD_REQUEST);
//      return response;
    }
    try {
      User user = new User();
      user.setUsername(accountDto.getUserName());
      user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
      Role curRole = roleRepository.findByName("USER");
      user.setRoles(
        Arrays.asList(curRole)
      );
      userRepository.save(user);
      response.setMessage("Saved");
      return new ResponseEntity<MessageDto>(HttpStatus.OK);
    } catch (Exception e) {
      response.setMessage("Cannot save user");
      return new ResponseEntity<MessageDto>(HttpStatus.BAD_GATEWAY);
    }
  }

  public ResponseEntity<MessageDto> logoutUser() {
    return new ResponseEntity<>(new MessageDto("OK"), HttpStatus.OK);
  }
}
