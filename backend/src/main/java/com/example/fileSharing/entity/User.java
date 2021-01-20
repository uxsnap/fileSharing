package com.example.fileSharing.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User implements UserDetails {
  @Id
  @GeneratedValue( generator = "uuid2" )
  @Type(type="uuid-char")
  @Column(name = "id")
  private UUID id;

  @Column(name = "username")
  private String username;

  @Column(name = "enabled")
  private boolean enabled;

  @Column(name = "password")
  private String password;

  @Column(name = "token_expired")
  private boolean tokenExpired;

  @Column(name = "avatar")
  private String avatar;

  @OneToMany(
    mappedBy = "user",
    orphanRemoval = true,
    cascade = CascadeType.PERSIST
  )
  @JsonBackReference
  private List<File> files;

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.PERSIST
  )
  @Where(clause = "STATUS = 0")
  private List<UserFriend> userFriends;

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }
}
