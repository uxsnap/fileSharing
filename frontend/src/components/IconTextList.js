import { IconText, NoInfo } from '.';
import React from 'react';

export const IconTextList = ({ items }) => {
  return (
    <ul className="icon-text-list">
      { items && items.length ? items.map((item) => (
        <IconText {...item} />
      )) : <NoInfo />}
    </ul>
  );
};

export default IconTextList;