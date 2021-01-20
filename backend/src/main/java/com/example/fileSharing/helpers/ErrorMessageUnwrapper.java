package com.example.fileSharing.helpers;

import com.example.fileSharing.dto.MessageDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

public class ErrorMessageUnwrapper {
  public static List<String> errors(Errors errors) {
    return  errors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());
  }
}
