package com.example.fileSharing.service;

import com.example.fileSharing.dto.UserInfoDto;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.helpers.CurrentLoggedUser;
import com.example.fileSharing.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public User loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(s);
    if (user == null) {
      throw new UsernameNotFoundException("Not found!");
    }
    return user;
  }

  public User findByEmail(String email) throws NullPointerException {
    return userRepository.findByEmail(email);
  }

  public String getAvatar(String userName) throws Exception {
    try {
      User user = this.loadUserByUsername(userName);
      return user.getAvatar();
    } catch (Exception e) {
      throw new Exception("Problem with fetching avatar");
    }
  }

  public List<UserInfoDto> getUsers(String userName) throws Exception {
    try {
      String currentUser = CurrentLoggedUser.getCurrentUser();
      return userRepository.findAllNonFriends(currentUser, userName);
    } catch (Exception e) {
      throw new Exception("Cannot get users");
    }
  }
}
