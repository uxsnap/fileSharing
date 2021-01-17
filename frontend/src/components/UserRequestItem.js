import React from 'react';
import { UserListItem, IconList } from './index';

export const UserRequestItem = ({ img, text, icons }) => (
  <UserListItem img={img} text={text} className="user-request-item">
    {icons && 
      <div className="user-request-item__icons">
        <IconList items={icons} />  
      </div>
    }
  </UserListItem>
);

export default UserRequestItem;