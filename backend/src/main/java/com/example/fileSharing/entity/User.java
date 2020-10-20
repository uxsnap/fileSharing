package com.example.fileSharing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
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

  @ManyToMany(fetch = FetchType.LAZY)
  private Collection<Role> roles;

  @OneToMany(
    fetch = FetchType.LAZY,
    mappedBy = "user",
    orphanRemoval = true,
    cascade = CascadeType.PERSIST
  )
  private List<File> files;

  @OneToMany(
    fetch = FetchType.LAZY,
    mappedBy = "user",
    orphanRemoval = true,
    cascade = CascadeType.PERSIST
  )
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
