package com.example.fileSharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterDto extends PasswordDto  {
  @NotBlank(message = "Email must not be empty")
  @Size(max = 255, message = "The length of email must be less than 255")
  @Pattern(
    regexp = ".*@.*",
    message = "Email must have '@' symbol"
  )
  protected String email;

  @NotBlank(message = "Username must not be empty")
  @Size(max = 255, message = "Username's length must be less than 255")
  protected String userName;
}
