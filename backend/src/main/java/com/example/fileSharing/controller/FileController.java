package com.example.fileSharing.controller;

import com.example.fileSharing.dto.FileNameDto;
import com.example.fileSharing.dto.FilesDto;
import com.example.fileSharing.dto.JsonResponse;
import com.example.fileSharing.dto.MessageDto;
import com.example.fileSharing.entity.File;
import com.example.fileSharing.repository.FileRepository;
import com.example.fileSharing.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController {
  private final FileRepository fileRepository;
  private final FileService fileService;

  @GetMapping("/{userName}")
  public ResponseEntity<Object> getUserFiles(@PathVariable(name = "userName") String userName) {
    try {
      List<File> files = fileService.getAllUserFiles(userName);
      return new ResponseEntity<>(new FilesDto(files), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new FilesDto(new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/{userName}")
  public ResponseEntity<Object> uploadFile(
    @PathVariable(name = "userName") String userName,
    @RequestParam(name = "file") MultipartFile file
  ) throws IOException {
    try {
      fileService.uploadFile(userName, file);
      return new ResponseEntity<>(new MessageDto("OK"), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new MessageDto("Error while fetching uploading file"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PatchMapping("/{fileId}")
  public ResponseEntity<MessageDto> editFile(
    @PathVariable(name = "fileId") UUID fileId,
    @RequestBody FileNameDto fileName
  ) {
    try {
      fileService.editFile(fileId, fileName.getFileName());
      return new ResponseEntity<>(new MessageDto("OK"), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new MessageDto("Problem with editing the file"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{fileId}")
  public ResponseEntity<MessageDto> deleteFile(
    @PathVariable(name = "fileId") UUID fileId
  ) {
    try {
      fileService.deleteFile(fileId);
      return new ResponseEntity<>(new MessageDto("OK"), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new MessageDto("Problem with deleting the file"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
