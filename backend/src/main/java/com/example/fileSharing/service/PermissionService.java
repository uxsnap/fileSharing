//package com.example.fileSharing.service;
//
//import com.example.fileSharing.dto.FilePrivilegeDto;
//import com.example.fileSharing.dto.JsonResponse;
//import com.example.fileSharing.entity.File;
//import com.example.fileSharing.entity.FileClient;
//import com.example.fileSharing.entity.User;
//import com.example.fileSharing.entity.UserFriend;
//import com.example.fileSharing.helpers.CurrentLoggedUser;
//import com.example.fileSharing.repository.FileClientRepository;
//import com.example.fileSharing.repository.FileRepository;
//import com.example.fileSharing.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
////@Service
//@AllArgsConstructor
//public class PermissionService {
//  private final UserRepository userRepository;
//  private final FileClientRepository fileClientRepository;
//  private final FileRepository fileRepository;
//
//  public JsonResponse giveFilePermission(FilePrivilegeDto filePrivilegeDto) {
//    String currentUser = CurrentLoggedUser.getCurrentUser();
//    User user = userRepository.findByUsername(currentUser);
//    try {
//      UserFriend foundFriend = user
//        .getUserFriends()
//        .stream()
//        .filter(friend -> friend.getUserName().equals(filePrivilegeDto.getUserName()))
//        .findFirst().get();
//
//      Optional<File> file = fileRepository.findById(filePrivilegeDto.getFileId());
//      if (!file.isPresent()) {
//        return new JsonResponse("Cannot find file", HttpStatus.BAD_REQUEST.value());
//      }
//
//      FileClient fileClient = new FileClient();
//      fileClient.setPrivilege(filePrivilegeDto.getPrivilege().getPrivilege());
//      fileClient.setUserName(foundFriend.getUserName());
//      fileClient.setFile(file.get());
//
//      fileClientRepository.save(fileClient);
//
//      return new JsonResponse(
//        "Ok",
//        HttpStatus.OK.value()
//      );
//    } catch (Exception e) {
//      e.printStackTrace();
//      return new JsonResponse(
//        "Error while giving permissions",
//        HttpStatus.BAD_REQUEST.value()
//      );
//    }
//  }
//}
