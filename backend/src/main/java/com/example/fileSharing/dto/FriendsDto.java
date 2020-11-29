package com.example.fileSharing.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class FriendsDto {
  private List<UUID> users;
}
