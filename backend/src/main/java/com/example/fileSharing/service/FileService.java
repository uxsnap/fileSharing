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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.example.fileSharing.helpers.ConstantClass.AVATAR_FOLDER;
import static com.example.fileSharing.helpers.ConstantClass.UPLOADED_FOLDER;

@Service
@AllArgsConstructor
public class FileService {
  private final UserRepository userRepository;
  private final FileRepository fileRepository;
  private final FileClientRepository fileClientRepository;

  public List<File> getAllUserFiles(String userName) {
    User user = userRepository.findByUsername(userName);
    return fileRepository.findAllByUserId(user.getId());
  }

  public void deleteFile(UUID fileId) {
    String currentUser = CurrentLoggedUser.getCurrentUser();
    try {
      User user = userRepository.findByUsername(currentUser);
      File file = user.getFiles().stream()
        .filter(item -> item.getId()
          .equals(fileId))
        .findFirst()
        .orElse(null);
      if (file == null) throw new NullPointerException("No needed file");
      UUID curFileId = file.getId();
      FileClient fileClient = fileClientRepository.findByFile_IdAndPrivilege(
        curFileId,
        PrivilegeEnum.DELETE.getPrivilege()
      );
      if (fileClient == null) throw new NullPointerException("No such privilege");
      fileClientRepository.deleteByFileId(curFileId);
      fileRepository.deleteFileById(curFileId);
      Files.delete(Paths.get(file.getLink()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

//  Work out needed exceptions
  public void uploadFile(String userName, MultipartFile file) throws Exception {
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
    if (user == null) throw new UsernameNotFoundException("Cannot find user with this username");

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
  }

  public void editFile(UUID fileId, String fileName) {
    fileRepository.editFileName(fileId, fileName);
  }

  public void uploadAvatar(String userName, MultipartFile avatar) throws Exception {
    String currentUser = CurrentLoggedUser.getCurrentUser();
    if (!currentUser.equals(userName)) throw new UsernameNotFoundException("Wrong user");
    byte[] bytes = avatar.getBytes();
    String curFileExtension =
      Objects.requireNonNull(avatar
        .getOriginalFilename())
        .split("\\.")[1];
    if (!curFileExtension.matches("png|jpe?g")) throw new Exception("Wrong exception");
    User user = userRepository.findByUsername(userName);
    if (user == null) throw new UsernameNotFoundException("User cannot be found");
    String prevAvatar = user.getAvatar();
    if (prevAvatar != null) {
      Path prevFilePath = Paths.get(prevAvatar);
      Files.delete(prevFilePath);
    }
    String curFileName = String.format(
      "%s.%s",
      UUID.randomUUID().toString(),
      curFileExtension
    );
    String path = AVATAR_FOLDER + curFileName;
    Path curPath = Paths.get(path);
    user.setAvatar(path);
    try {
      Files.write(curPath, bytes);
      userRepository.save(user);
    } catch (IOException exception) {
      throw new IOException("Cannot save avatar-file");
    }
  }
}
