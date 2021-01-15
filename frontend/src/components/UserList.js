import React, { useState } from 'react';
import OutsideClickHandler from 'react-outside-click-handler';
import { Icon, UserItemList } from '.';

export const FriendRequests = ({ icon, items, Component }) => {
  const [isActive, setActive] = useState(false);

  const toggleSetActive = () => setActive(!isActive);

  return (
    <OutsideClickHandler onOutsideClick={() => setActive(false)}>
      <div onClick={toggleSetActive} class="user-list">
        <div class="user-list__icon">
          <Icon iconType={icon ? icon : ''} />
        </div>
        {isActive && <div class="user-list__list">
          <UserItemList items={items} Component={Component} />
        </div>}
      </div>
    </OutsideClickHandler>
  );
};

export default FriendRequests;