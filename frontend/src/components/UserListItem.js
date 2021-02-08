import React from 'react';
import { Avatar, RoundedInitials } from './index';

export const UserListItem = ({ className, img, text, children }) => (
  <div className={`user-list-item ${className ? className : ''}`}>
    <div className="user-list-item__img">
      {img ? <Avatar src={img} /> : <RoundedInitials name={text} />}
    </div>
    <div className="user-list-item__text">
      {text}
    </div>
    {children}
  </div>
);

export default UserListItem;