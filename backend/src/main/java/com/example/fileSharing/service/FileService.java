package com.example.fileSharing.service;

import com.example.fileSharing.dto.FileShortInfoDto;
import com.example.fileSharing.entity.File;
import com.example.fileSharing.entity.FileClient;
import com.example.fileSharing.entity.User;
import com.example.fileSharing.entity.UserFriend;
import com.example.fileSharing.helpers.CurrentLoggedUser;
import com.example.fileSharing.repository.FileClientRepository;
import com.example.fileSharing.repository.FileRepository;
import com.example.fileSharing.repository.UserRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.fileSharing.helpers.ConstantClass.AVATAR_FOLDER;

@Service
@AllArgsConstructor
public class FileService {
  private final static String UPLOADED_FOLDER = "backend/src/files/";

  private final UserRepository userRepository;
  private final FileRepository fileRepository;
  private final FileClientRepository fileClientRepository;

  public List<File> getAllUserFiles() {
    String currentUser = CurrentLoggedUser.getCurrentUser();
    User user = userRepository.findByUsername(currentUser);
    return fileRepository.findAllByUserId(user.getId());
  }

  public File getFriendsFileById(UUID fileId) {
    File requiredFile = null;
    String currentUser = CurrentLoggedUser.getCurrentUser();
    List<UserFriend> friends = userRepository.findByUsername(currentUser)
      .getUserFriends();
    for (UserFriend friend : friends) {
      List<File> files = friend.getFriendProfile().getFiles();
      requiredFile = files.stream().filter(file -> file.getId().equals(fileId)).findFirst().orElse(null);
      if (requiredFile != null)
        break;
    }
    return requiredFile;
  }

  public List<FileShortInfoDto> getFriendFiles(UUID friendId) {
    String currentUser = CurrentLoggedUser.getCurrentUser();
    try {
      User user = userRepository.findByUsername(currentUser);
      List<UserFriend> userFriends = user.getUserFriends();
      UserFriend userFriend = userFriends.stream()
          .filter(u -> u.getFriendProfile().getId().equals(friendId))
          .findFirst()
          .orElseThrow(() -> new NotFoundException("Friend not found"));
      return userFriend
        .getFriendProfile()
        .getFiles()
        .stream().map(file -> new FileShortInfoDto(
           file.getId(), file.getOriginalName(), file.getLink()
        )).collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
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
      fileClientRepository.deleteByFileId(curFileId);
      fileRepository.deleteFileById(curFileId);
      Files.delete(Paths.get(file.getLink()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

//  Work out needed exceptions
  public void uploadFile(MultipartFile file) throws Exception {
    String userName = CurrentLoggedUser.getCurrentUser();
    byte[] bytes = file.getBytes();
    String curFileExtension =
      Objects.requireNonNull(file
        .getOriginalFilename())
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
    fileClientRepository.save(new FileClient(user.getUsername(), fileEntity));
  }

  public void editFile(UUID fileId, String fileName) {
    fileRepository.editFileName(fileId, fileName);
  }

  public void uploadAvatar(MultipartFile avatar) throws Exception {
    String currentUser = CurrentLoggedUser.getCurrentUser();
    byte[] bytes = avatar.getBytes();
    String curFileExtension =
      Objects.requireNonNull(avatar
        .getOriginalFilename())
        .split("\\.")[1];
    if (!curFileExtension.matches("png|jpe?g")) throw new Exception("Wrong file format");
    User user = userRepository.findByUsername(currentUser);
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
    Path folder = Paths.get(AVATAR_FOLDER);

    if (!Files.exists(folder)) {
      Files.createDirectory(folder);
    }

    String path = AVATAR_FOLDER + curFileName;
    Path curPath = Paths.get(path);
    user.setAvatar(path.replace("backend/src", ""));
    try {
      Files.write(curPath, bytes);
      userRepository.save(user);
    } catch (IOException exception) {
      throw new IOException("Cannot save avatar-file");
    }
  }
}
