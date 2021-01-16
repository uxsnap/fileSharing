package com.example.fileSharing.entity;

import com.example.fileSharing.helpers.FriendRequestStatusEnum;
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

  @Column(name = "status")
  private FriendRequestStatusEnum status;

  public UserFriend(User user, User friendProfile, FriendRequestStatusEnum status) {
    this.user = user;
    this.friendProfile = friendProfile;
    this.status = status;
  }
}
