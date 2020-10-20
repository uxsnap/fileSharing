package com.example.fileSharing.dto;

import com.example.fileSharing.helpers.PrivilegeEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class FilePrivilegeDto {
  private String userName;
  private UUID fileId;
  private PrivilegeEnum privilege;
}
