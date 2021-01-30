package com.example.fileSharing.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailMessageDto {
  private String subject;
  private String text;
  private String to;
  private String from;
}
