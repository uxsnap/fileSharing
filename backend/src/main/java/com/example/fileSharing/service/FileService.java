package com.example.fileSharing.service;

import com.example.fileSharing.dto.JsonResponse;
import com.example.fileSharing.entity.File;
import com.example.fileSharing.entity.FileClient;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.helpers.CurrentLoggedUser;
import com.example.fileSharing.helpers.PrivilegeEnum;
import com.example.fileSharing.repository.FileClientRepository;
import com.example.fileSharing.repository.FileRepository;
import com.example.fileSharing.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.example.fileSharing.helpers.ConstantClass.UPLOADED_FOLDER;

@Service
@AllArgsConstructor
public class FileService {
  private final UserRepository userRepository;
  private final FileRepository fileRepository;
  private final FileClientRepository fileClientRepository;

  public List<File> getAllUserFiles(String userName) {
    User user = userRepository.findByUsername(userName);
    String currentUser = CurrentLoggedUser.getCurrentUser();
    if (user == null || !userName.equals(currentUser)) return Collections.emptyList();
    return fileRepository.findAllByUserId(user.getId());
  }

  public JsonResponse deleteFile(UUID fileId) {
    String currentUser = CurrentLoggedUser.getCurrentUser();
    try {
      User user = userRepository.findByUsername(currentUser);
      List<File> files = user.getFiles();
      for (File file : files) {
        if (file.getId().equals(fileId)) {
          String link = file.getLink();
          fileRepository.deleteFileById(fileId);
          Files.delete(Paths.get(link));
          return new JsonResponse("Deleted", HttpStatus.OK.value());
        }
      }
      return new JsonResponse(
        "Cannot find user's file",
        HttpStatus.BAD_REQUEST.value()
      );

    } catch (Exception e) {
      e.printStackTrace();
      return new JsonResponse(
        "Problem with deleting the file",
        HttpStatus.BAD_REQUEST.value()
      );
    }
  }

  public JsonResponse uploadFile(String userName, MultipartFile file) throws IOException {
    String currentUser = CurrentLoggedUser.getCurrentUser();
    if (!currentUser.equals(userName))
      return new JsonResponse("Wrong user", HttpStatus.BAD_REQUEST.value());
    byte[] bytes = file.getBytes();
    String curFileExtension =
      file
        .getOriginalFilename()
        .split("\\.")[1];
    String curFileName = String.format(
      "%s.%s",
      UUID.randomUUID().toString(),
      curFileExtension
    );

    String path = UPLOADED_FOLDER + curFileName;
    User user = userRepository.findByUsername(userName);
    if (user == null)
      return new JsonResponse("User not found", HttpStatus.UNAUTHORIZED.value());

    FileOutputStream output = new FileOutputStream(path);
    output.write(bytes);
    output.close();
    File fileEntity = new File();

    fileEntity.setUser(user);
    fileEntity.setName(curFileName);
    fileEntity.setSize(file.getSize());
    fileEntity.setLink(path);
    fileEntity.setOriginalName(file.getOriginalFilename());

    fileRepository.save(fileEntity);

    List<PrivilegeEnum> privilegeEnums = Arrays.asList(
      PrivilegeEnum.LOAD, PrivilegeEnum.DELETE, PrivilegeEnum.RENAME
    );

    for (PrivilegeEnum privilegeEnum: privilegeEnums) {
      fileClientRepository.save(new FileClient(
        user.getUsername(),
        privilegeEnum.getPrivilege(),
        fileEntity
      ));
    }
    return new JsonResponse("Ok", HttpStatus.OK.value());
  }
}
