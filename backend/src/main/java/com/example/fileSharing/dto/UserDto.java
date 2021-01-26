package com.example.fileSharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
  @NotBlank(message = "Username must not be empty")
  @Size(max = 255, message = "Username's length must be less than 255")
  protected String userName;
}
