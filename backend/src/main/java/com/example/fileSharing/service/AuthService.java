package com.example.fileSharing.service;

import com.example.fileSharing.dto.UserDto;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User registerNewUserAccount(UserDto accountDto)  {
    User user = new User();

    user.setUsername(accountDto.getUsername());
    user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
//    user.setEmail(accountDto.getEmail());

//    user.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
    return userRepository.save(user);
  }

//  private boolean emailExist(String email) {
//    return userRepository.findByEmail(email) != null;
//  }
}
