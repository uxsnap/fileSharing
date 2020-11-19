package com.example.fileSharing.repository;

import com.example.fileSharing.dto.UserInfoDto;
import com.example.fileSharing.entity.File;
import com.example.fileSharing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
//  User findByEmail(String email);
  User findByUsername(String username);

  @Query("SELECT new com.example.fileSharing.dto.UserInfoDto(u.username, u.avatar) " +
    "FROM User u where u.username like CONCAT('%', ?2, '%') and u.username <> ?1 and u.id not in " +
    "(select u.id from u join UserFriend uf on u.id = uf.user.id)")
  List<UserInfoDto> findAllNonFriends(String userName, String search);
}
