import React from 'react';
import { UserListItem, Icon } from './index';

export const FriendRequest = ({ id, img, text, icons, onIconClick, checked }) => (
  <UserListItem img={img} text={text} className="avatar-item">
    {icon && <div className={`avatar-item__icon ${!checked && 'active' }`}>
        <IconList 
          items={icons}
        />
      </div>
    }
  </UserListItem>
);

export default FriendRequest;