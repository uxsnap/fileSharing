package com.example.fileSharing.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "ROLE")
@Getter
@NoArgsConstructor
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @Column(name = "name")
  private String name;

  @ManyToMany(mappedBy = "roles")
  @JsonBackReference
  private Collection<User> users;

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private Set<Privilege> privileges;
}
