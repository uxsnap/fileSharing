import React, { useRef } from 'react';
import { IconTextList } from './index';
import { lazyRender } from 'utils';

export const FilesContextMenu = ({ onLoad, active, files, userId, onMouseLeave, status }) => {
  const elem = document.querySelector('.' + userId);
  const menu = useRef(null);
  const innerOnMouseLeave = (e) => {
    onMouseLeave(e); 
    e.stopPropagation();
  }

  const preparedFiles = files.map((file) => ({ ...file, onClick: () => onLoad(file.id, file.text) }));

  return (
    <div
      ref={menu}
      className={`files-context-menu ${active ? 'active' : ''}`}
      style={{ top: elem.offsetTop + 'px' }}
      onMouseLeave={innerOnMouseLeave}
    >
      {
        lazyRender(
          <IconTextList items={preparedFiles}/>,
          status
        )
      }
    </div>
  )
};

export default FilesContextMenu;