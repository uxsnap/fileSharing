import React, { useState } from 'react';
import { AvatarItem, FilesContextMenu } from './index';

export default ({ user, onMouseEnter, active }) => {
  const currentUserSelector = `user-files__user_${user.id}`;
  const innerOnMouseEnter = (e) => {
    onMouseEnter(user.id, currentUserSelector);
    e.stopPropagation();
  }
  return (
    <div
      onMouseEnter={innerOnMouseEnter}
      className={`user-files ${active ? 'active' : ''}`}
    >
      <div className={`user-files__user ${currentUserSelector}`}>
        <AvatarItem {...user} />
      </div>
    </div>
  )
};