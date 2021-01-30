package com.example.fileSharing.service;

import com.example.fileSharing.dto.*;
import com.example.fileSharing.entity.PasswordResetToken;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.helpers.ConstantClass;
import com.example.fileSharing.helpers.UserAlreadyExistAuthenticationException;
import com.example.fileSharing.repository.PasswordResetTokenRepository;
import com.example.fileSharing.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordResetTokenRepository passwordResetTokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Value("${spring.custom.reset_pass_template}")
  private String resetPassTemplate;

  @Value("${spring.custom.support_email}")
  private String supportEmail;

  public JwtResponse loginUser(LoginDto userDto) {
    JwtResponse jwtResponse = new JwtResponse();
    try {
      String email = userDto.getEmail();

      Authentication authentication = new UsernamePasswordAuthenticationToken(
        email,
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
      jwtResponse.setEmail(email);
      jwtResponse.setErrors(new ArrayList<>());
      return jwtResponse;
    } catch (Exception e) {
      jwtResponse.setErrors(Collections.singletonList("Cannot login!"));
      return jwtResponse;
    }
  }

  public void registerNewUserAccount(RegisterDto accountDto)  {
    User found = userRepository.findByUsernameOrEmail(
      accountDto.getUserName(), accountDto.getEmail()
    );
    if (found != null) {
      throw new UserAlreadyExistAuthenticationException("User already created!");
    }
    try {
      User user = new User();
      user.setEmail(accountDto.getEmail());
      user.setUsername(accountDto.getUserName());
      user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
      userRepository.save(user);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private MailMessageDto constructResetTokenEmail(UUID token, User user) {
    String url = resetPassTemplate + "/forgotPassword/" + token.toString();
    return constructEmail("Here is your link to password reset: " + " \r\n" + url, user);
  }

  private MailMessageDto constructEmail(String body,
                                        User user) {
    MailMessageDto email = new MailMessageDto();
    email.setSubject("Reset Password");
    email.setText(body);
    email.setTo(user.getEmail());
    email.setFrom(supportEmail);
    return email;
  }

  public void resetUserPassword(User user) {
    UUID token = UUID.randomUUID();
    PasswordResetToken passwordResetToken = new PasswordResetToken(
      token, user
    );
    passwordResetTokenRepository.save(passwordResetToken);
    constructResetTokenEmail(token, user);
  }

  public void logoutUser() {
  }
}
