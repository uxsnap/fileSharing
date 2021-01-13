import React, { useRef } from 'react';
import { IconTextList } from './index';
import { lazyRender } from 'utils';

export const FilesContextMenu = ({ active, files, userId, onMouseLeave, status, Loader }) => {
  const elem = document.querySelector('.' + userId);
  const menu = useRef(null);
  const innerOnMouseLeave = (e) => {
    onMouseLeave(e); 
    e.stopPropagation();
  }
  return (
    <div
      ref={menu}
      className={`files-context-menu ${active ? 'active' : ''}`}
      style={{ top: elem.offsetTop + 'px' }}
      onMouseLeave={innerOnMouseLeave}
    >
      {
        lazyRender(
          <IconTextList items={files}/>,
          status
        )
      }
    </div>
  )
};

export default FilesContextMenu;