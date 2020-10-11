package com.example.fileSharing.helpers;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PrivilegeEnum {
  READ("file:read"),
  READ_ALL("file:read_all"),
  ADD("file:add"),
  ADD_ALL("file:add_all"),
  DELETE("file:delete"),
  DELETE_ALL("file:delete_all"),
  UPDATE("file:update"),
  UPDATE_ALL("file:update_all");

  private final String privilege;

  public String getPrivilege() { return privilege; }
}
