import React, { useRef } from 'react';
import { IconText } from './index';

export default ({ active, files, userId, onMouseLeave }) => {
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
      <ul className="files-context-menu__list">
        {files && files.map((item) => (
          <IconText {...item} />
        ))}
      </ul>
    </div>
  )
};