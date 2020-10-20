package com.example.fileSharing.helpers;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;

import static com.example.fileSharing.helpers.PrivilegeEnum.*;

import java.util.Set;

@AllArgsConstructor
public enum RoleEnum {
  USER(
    Sets.newHashSet(
      LOAD,
      DELETE,
      RENAME
    )
  );

  private Set<PrivilegeEnum> privileges;

  public Set<PrivilegeEnum> getPrivileges() { return privileges; }
}
