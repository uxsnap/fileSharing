package com.example.fileSharing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "PRIVILEGE")
@Data
@NoArgsConstructor
public class Privilege {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @Column(name = "name")
  private String name;

  @ManyToMany(mappedBy = "privileges")
  private Collection<Role> roles;
}
