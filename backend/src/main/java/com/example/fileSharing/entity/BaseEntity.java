package com.example.fileSharing.entity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

public class BaseEntity {
  @Id
  @GeneratedValue( generator = "uuid2" )
  @Type(type="uuid-char")
  @Column(name = "id")
  protected UUID id;
}
