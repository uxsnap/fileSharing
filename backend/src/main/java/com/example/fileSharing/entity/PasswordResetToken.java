package com.example.fileSharing.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "password_reset")
public class PasswordResetToken {
  @Id
  @GeneratedValue( generator = "uuid2" )
  @Type(type = "uuid-char")
  private UUID id;

  @Type(type = "uuid-char")
  private UUID token;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "userId")
  private User user;

  private Date expireDate;

  public PasswordResetToken(UUID token, User user) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.DATE, 1);

    this.token = token;
    this.user = user;
    this.expireDate = cal.getTime();
  }
}
