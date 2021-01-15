package com.example.fileSharing.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FriendRequestStatusEnum {
  ACCEPTED("accepted"),
  PENDING("pending");

  private final String status;
}
