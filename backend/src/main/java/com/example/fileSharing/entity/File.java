package com.example.fileSharing.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "file")
@NoArgsConstructor
public class File {
  @Id
  @GeneratedValue( generator = "uuid2" )
  @Type(type="uuid-char")
  @Column(name = "id")
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "size")
  private double size;

  @Column(name = "link")
  private String link;

  @Column(name = "original_name")
  private String originalName;

  @OneToMany(
    fetch = FetchType.LAZY,
    mappedBy = "file",
    orphanRemoval = true,
    cascade = CascadeType.ALL
  )
  private Set<FileClient> fileClients;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonBackReference
  private User user;
}
