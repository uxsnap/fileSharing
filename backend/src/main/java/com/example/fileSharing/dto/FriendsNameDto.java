package com.example.fileSharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendsNameDto extends MessageDto {
  private List<String> friendNames;
}
