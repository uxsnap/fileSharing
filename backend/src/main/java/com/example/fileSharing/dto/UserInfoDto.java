package com.example.fileSharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
  private UUID id;
  private String userName;
  private String avatar;

  public UserInfoDto(String userName) {
    this.userName = userName;
  }

  public UserInfoDto(UUID id, String userName) {
    this.id = id;
    this.userName = userName;
  }
}
