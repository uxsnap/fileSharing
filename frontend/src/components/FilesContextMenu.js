import React from 'react';
import { IconText } from './index';

export default ({ files }) => {
  return (
    <div className="files-context-menu">
      <ul class="files-context-menu__list">
        {files.map((item) => (
          <IconText {...item} />
        ))}
      </ul>
    </div>
  )
};