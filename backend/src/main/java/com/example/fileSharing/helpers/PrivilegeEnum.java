package com.example.fileSharing.helpers;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PrivilegeEnum {
  LOAD("file:load"),
  DELETE("file:delete"),
  RENAME("file:rename");

  private final String privilege;

  public String getPrivilege() { return privilege; }
}
