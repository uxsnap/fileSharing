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

  @Type(type="uuid-char")
  @Column(name = "friend_id")
  private UUID friendId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable =  false)
  private User user;

  public UserFriend(UUID friendId, User user) {
    this.friendId = friendId;
    this.user = user;
  }
}
