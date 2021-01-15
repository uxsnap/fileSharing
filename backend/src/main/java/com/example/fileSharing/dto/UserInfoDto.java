package com.example.fileSharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserInfoDto extends UserIdDto {
  private String userName;
  private String avatar;

  public UserInfoDto(String userName) {
    this.userName = userName;
  }

  public UserInfoDto(UUID id, String userName, String avatar) {
    super(id);
    this.userName = userName;
    this.avatar = avatar;
  }

  public UserInfoDto(UUID id, String userName) {
    this.id = id;
    this.userName = userName;
  }
}
