package com.example.fileSharing.service;

import com.example.fileSharing.dto.JsonResponse;
import com.example.fileSharing.dto.UserDto;
import com.example.fileSharing.entity.Privilege;
import com.example.fileSharing.entity.Role;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.helpers.RoleEnum;
import com.example.fileSharing.repository.RoleRepository;
import com.example.fileSharing.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import static com.example.fileSharing.helpers.ConstantClass.DEFAULT_USER_ID;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public JsonResponse registerNewUserAccount(UserDto accountDto)  {
    JsonResponse response = new JsonResponse();
    User found = userRepository.findByUsername(accountDto.getUsername());
    if (found != null) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setMessage("User already exists");
      return response;
    }
    try {
      User user = new User();
      user.setUsername(accountDto.getUsername());
      user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
      Role curRole = roleRepository.findByName("USER");
      user.setRoles(
        Arrays.asList(curRole)
      );
      userRepository.save(user);
      response.setStatus(HttpStatus.OK.value());
      response.setMessage("Saved");
      return response;
    } catch (Exception e) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value()
      );
      response.setMessage("Cannot save user");
      return response;
    }
  }
}
