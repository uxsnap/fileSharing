package com.example.fileSharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class FileShortInfoDto extends FileNameDto {
  private UUID fileId;
  private String fileLink;

  public FileShortInfoDto(
      UUID fileId, String fileName, String fileLink
  ) {
    super(fileName);
    this.fileId = fileId;
    this.fileLink = fileLink;
  }
}
