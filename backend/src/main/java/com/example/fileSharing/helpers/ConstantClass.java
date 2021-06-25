package com.example.fileSharing.helpers;

import com.google.common.collect.ImmutableList;

public class ConstantClass {
  public static String AVATAR_FOLDER = "backend/src/avatars/";
  public static String SECRET_KEY = "Very secret key. Very very secret key there";

  public static ImmutableList<String> ALLOWED_METHODS = ImmutableList
    .of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH");

  public static ImmutableList<String> ALLOWED_HEADERS = ImmutableList
    .of("Authorization", "Cache-Control", "Content-Type");
}
