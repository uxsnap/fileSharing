package com.example.fileSharing.service;

import com.example.fileSharing.dto.AvatarDto;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.helpers.CurrentLoggedUser;
import com.example.fileSharing.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public User loadUserByUsername(String s) throws UsernameNotFoundException {
    return userRepository.findByUsername(s);
  }

  public String getAvatar(String userName) {
    String currentUser = CurrentLoggedUser.getCurrentUser();
    if (!currentUser.equals(userName)) throw new UsernameNotFoundException("Wrong user");
    User user = this.loadUserByUsername(userName);
    return user.getAvatar();
  }
}
