package com.example.fileSharing.controller;

import com.example.fileSharing.dto.FilePrivilegeDto;
import com.example.fileSharing.dto.JsonResponse;
import com.example.fileSharing.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
@AllArgsConstructor
public class PermissionController {
  private final PermissionService permissionService;

  @PostMapping("/give")
  public JsonResponse giveFilePermission(
    @RequestBody FilePrivilegeDto filePermissionDto
  ) {
    return permissionService.giveFilePermission(filePermissionDto);
  }
}
