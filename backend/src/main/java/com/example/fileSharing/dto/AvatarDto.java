package com.example.fileSharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvatarDto extends MessageDto {
  private String avatar;
}
