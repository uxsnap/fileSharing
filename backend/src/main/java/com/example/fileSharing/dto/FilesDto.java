package com.example.fileSharing.dto;

import com.example.fileSharing.entity.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilesDto {
  private List<File> files;
}
