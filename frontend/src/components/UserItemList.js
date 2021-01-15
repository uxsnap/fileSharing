import { AvatarItem, NoInfo } from '.';
import React from 'react';

export const AvatarItemList = ({ Component, items }) => {
  return (
    <ul className="user-item-list">
      { items && items.length ? items.map((item) => (
        <Component {...item} />
      )) : <NoInfo />}
    </ul>
  );
};

export default AvatarItemList;