package com.example.fileSharing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "USER")
@Data
@NoArgsConstructor
public class User implements UserDetails {
  @Id
  @GeneratedValue
  private UUID id;

  @Column(name = "username")
  private String username;

//  @Column(name = "email")
//  private String email;

  @Column(name = "enabled")
  private boolean enabled;

  @Column(name = "password")
  private String password;

  @Column(name = "token_expired")
  private boolean tokenExpired;

  @ManyToMany(fetch = FetchType.LAZY,
    cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private Set<Privilege> privileges;

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
