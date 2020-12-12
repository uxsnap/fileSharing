package com.example.fileSharing.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_friend")
public class UserFriend {
  @Id
  @GeneratedValue( generator = "uuid2" )
  @Type(type="uuid-char")
  @Column(name = "id")
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "friend_id", nullable = false)
  private User friendProfile;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable =  false)
  private User user;

  public UserFriend(User friendProfile, User user) {
    this.friendProfile = friendProfile;
    this.user = user;
  }
}
