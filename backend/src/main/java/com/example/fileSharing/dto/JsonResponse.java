package com.example.fileSharing.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class JsonResponse {
  private String message;
  private Integer status;

  public JsonResponse(String message, Integer status) {
    this.message = message;
    this.status = status;
  }

  public static JsonResponse ok() {
    return new JsonResponse("Ok", HttpStatus.OK.value());
  }
  public static JsonResponse wrong() {
    return new JsonResponse("Something went wrong", HttpStatus.BAD_REQUEST.value());
  }
}
