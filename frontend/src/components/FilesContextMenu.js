import React, { useRef } from 'react';
import { IconTextList, LazyRender } from '.';

export const FilesContextMenu = ({ onLoad, active, files, userId, onMouseLeave, status }) => {
  const menu = useRef(null);
  const innerOnMouseLeave = (e) => {
    onMouseLeave(e);
    e.stopPropagation();
  }
  const preparedFiles = files.map((file) => ({ ...file, onClick: () => onLoad(file.id, file.text) }));
  const getElem = (userId) => document.querySelector('.' + userId);
  const contextMenuClassName = () => `files-context-menu ${active ? 'active' : ''}`;

  return userId && (
    <div
      ref={menu}
      className={contextMenuClassName()}
      style={{top: getElem(userId).offsetTop + 'px'}}
      onMouseLeave={innerOnMouseLeave}
    >
      <LazyRender status={status}>
        <IconTextList items={preparedFiles}/>
      </LazyRender>
    </div>
  )
};

export default FilesContextMenu;