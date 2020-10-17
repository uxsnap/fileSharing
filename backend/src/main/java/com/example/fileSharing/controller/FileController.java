package com.example.fileSharing.controller;

import com.example.fileSharing.dto.JsonResponse;
import com.example.fileSharing.entity.File;
import com.example.fileSharing.repository.FileRepository;
import com.example.fileSharing.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController {
  private final FileRepository fileRepository;
  private final FileService fileService;

  @GetMapping("/{userName}")
  public List<File> getUserFiles(@PathVariable(name = "userName") String userName) {
    return fileService.getAllUserFiles(userName);
  }

  @PostMapping("/{userName}")
  public JsonResponse uploadFile(
    @PathVariable(name = "userName") String userName,
    @RequestParam(name = "file") MultipartFile file
  ) throws IOException {
    return fileService.uploadFile(userName, file);
  }

  @DeleteMapping("/{fileId}")
  public JsonResponse deleteFile(
    @PathVariable(name = "fileId") UUID fileId
  ) {
    return fileService.deleteFile(fileId);
  }
}
