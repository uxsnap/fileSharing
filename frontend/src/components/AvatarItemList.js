import { AvatarItem, NoInfo } from '.';
import React from 'react';

export const AvatarItemList = ({ items }) => {
  return (
    <ul className="avatar-item-list">
      { items && items.length ? items.map((item) => (
        <AvatarItem {...item} />
      )) : <NoInfo />}
    </ul>
  );
};

export default AvatarItemList;