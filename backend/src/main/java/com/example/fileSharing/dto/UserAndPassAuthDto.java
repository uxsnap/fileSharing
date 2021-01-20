package com.example.fileSharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAndPassAuthDto extends UserDto {
  @NotBlank(message = "Password must not be empty")
  @Size(min = 8, max = 255, message = "The length of password must be more than 8 and less than 255")
  @Pattern(
    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,255}$",
    message = "Password must include at least 1 uppercase letter, 1 digit and 1 lowercase letter"
  ) // Password regexp
  private String password;
}
