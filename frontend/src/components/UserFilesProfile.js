import React, { useState } from 'react';
import { AvatarItem, FilesContextMenu } from './index';

export default ({ user, onMouseEnter, active }) => {
  const currentUserId = `user-files__user_${user.id}`;
  const innerOnMouseEnter = (e) => {
    onMouseEnter(currentUserId);
    e.stopPropagation();
  }
  return (
    <div
      onMouseEnter={innerOnMouseEnter}
      className={`user-files ${active ? 'active' : ''}`}
    >
      <div className={`user-files__user ${currentUserId}`}>
        <AvatarItem {...user} />
      </div>
    </div>
  )
};