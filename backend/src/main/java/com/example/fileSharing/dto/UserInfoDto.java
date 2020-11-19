package com.example.fileSharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
  private String userName;
  private String avatar;

  public UserInfoDto(String userName) {
    this.userName = userName;
  }
}
