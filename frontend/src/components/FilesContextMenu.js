import React, { useRef } from 'react';
import { IconText, NoInfo } from './index';
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
          <ul className="files-context-menu__list">
            { files && files.length ? files.map((item) => (
              <IconText {...item} />
            )) : <NoInfo />}
          </ul>,
          status
        )
      }
    </div>
  )
};

export default FilesContextMenu;