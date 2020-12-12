import React, { useState } from 'react';
import { AvatarItem } from './index';

export default ({ user, files }) => {
  const [menuIsActive, setMenuIsActive] = useState(false);

  return (
    <div
      onMouseOver={() => setMenuIsActive(true)}
      className="user-files-context-menu"
      onMouseOut={() => setMenuIsActive(false)}
    >
      <div class="user-files-context-menu__user">
        <AvatarItem {...user} />
      </div>
      {menuIsActive &&
        <div class="user-files-context-menu__context-menu">
          <FilesContextMenu files={files}/>
        </div>
      }
    </div>
  )
};