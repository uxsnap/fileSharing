package com.example.fileSharing.entity;

import com.example.fileSharing.helpers.PrivilegeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.security.Permission;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "file_client")
@NoArgsConstructor
public class FileClient {
  @Id
  @GeneratedValue( generator = "uuid2" )
  @Type(type="uuid-char")
  @Column(name = "id")
  private UUID id;

  @Column(name = "username")
  private String userName;

  @Column(name = "privilege")
  private String privilege;

  public FileClient(String userName, String privilege, File file) {
    this.userName = userName;
    this.privilege = privilege;
    this.file = file;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "file_id", nullable = false)
  private File file;
}
