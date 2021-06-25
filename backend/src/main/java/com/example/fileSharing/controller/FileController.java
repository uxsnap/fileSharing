package com.example.fileSharing.controller;

import com.example.fileSharing.dto.FileNameDto;
import com.example.fileSharing.dto.FileShortInfoDto;
import com.example.fileSharing.dto.FilesDto;
import com.example.fileSharing.dto.MessageDto;
import com.example.fileSharing.entity.File;
import com.example.fileSharing.helpers.CurrentLoggedUser;
import com.example.fileSharing.repository.FileRepository;
import com.example.fileSharing.service.FileService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.aspectj.bridge.Message;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@RestController
@RequestMapping("file")
@AllArgsConstructor
public class FileController {
  private final FileService fileService;

  @GetMapping
  public ResponseEntity<FilesDto<File>> getUserFiles() {
    FilesDto<File> filesDto = new FilesDto<File>();
    try {
      List<File> files = fileService.getAllUserFiles();
      filesDto.setMessage("OK");
      filesDto.setFiles(files);
      return ResponseEntity.ok(filesDto);
    } catch (Exception e) {
      filesDto.setErrors(Collections.singletonList(e.getMessage()));
      return ResponseEntity.badRequest().body(filesDto);
    }
  }

  @GetMapping("/{fileId}/download")
  public ResponseEntity<Resource> download(
    @PathVariable(name = "fileId") UUID fileId
  ) {
    try {
      File file = fileService.getFriendsFileById(fileId);
      if (file == null) throw new NullPointerException("No such file");
      Path path = Paths.get(file.getLink());
      FileChannel fileChannel = FileChannel.open(path);
      long fileSize = fileChannel.size();
      ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
      // Needed headers
      return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getOriginalName())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .contentLength(fileSize)
        .body(resource);
    } catch (IOException ioException) {
      System.err.println("Problem with IO when downloading file");
      return ResponseEntity.badRequest().body(null);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body(null);
    }
  }

  @PostMapping
  public ResponseEntity<MessageDto> uploadFile(
    @RequestParam(name = "file") MultipartFile file
  ) {
    MessageDto messageDto = new MessageDto();
    try {
      fileService.uploadFile(file);
      messageDto.setMessage("OK");
      return ResponseEntity.ok(messageDto);
    } catch (Exception e) {
      messageDto.setErrors(Collections.singletonList(e.getMessage()));
      return ResponseEntity
        .badRequest()
        .body(messageDto);
    }
  }

  @PatchMapping("/{fileId}")
  public ResponseEntity<MessageDto> editFile(
    @PathVariable(name = "fileId") UUID fileId,
    @RequestBody FileNameDto fileName
  ) {
    MessageDto messageDto = new MessageDto();
    try {
      fileService.editFile(fileId, fileName.getFileName());
      messageDto.setMessage("OK");
      return ResponseEntity.ok(messageDto);
    } catch (Exception e) {
      messageDto.setErrors(Collections.singletonList(e.getMessage()));
      return ResponseEntity.badRequest()
        .body(messageDto);
    }
  }

  @DeleteMapping("/{fileId}")
  public ResponseEntity<MessageDto> deleteFile(
    @PathVariable(name = "fileId") UUID fileId
  ) {
    MessageDto messageDto = new MessageDto();
    try {
      messageDto.setMessage("OK");
      fileService.deleteFile(fileId);
      return ResponseEntity.ok(messageDto);
    } catch (Exception e) {
      messageDto.setErrors(Collections.singletonList(e.getMessage()));
      return ResponseEntity.badRequest()
        .body(messageDto);
    }
  }
}
