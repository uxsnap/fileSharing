import React, { useState } from 'react';
import OutsideClickHandler from 'react-outside-click-handler';
import { Icon, AvatarItemList } from '.';

export const FriendRequests = ({ friendList }) => {
  const [isActive, setActive] = useState(false);

  const toggleSetActive = () => setActive(!isActive);

  return (
    <OutsideClickHandler onOutsideClick={() => setActive(false)}>
      <div onClick={toggleSetActive} class="friend-requests">
        <div class="friend-requests__icon">
          <Icon iconType="user-friends" />
        </div>
        {isActive && <div class="friend-requests__list">
          <AvatarItemList items={friendList}/>
        </div>}
      </div>
    </OutsideClickHandler>
  );
};

export default FriendRequests;