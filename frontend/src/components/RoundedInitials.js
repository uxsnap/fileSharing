import React from 'react';
import { Icon, Avatar } from './index';

export default ({ name }) => (
  <div className="rounded-initials">
    <span>{name ? name[0].toUpperCase() : ''}</span>
  </div>
);
