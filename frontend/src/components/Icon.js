import React from 'react';

export default ({ iconType, onClick }) => (
  <span
      onClick={onClick ? onClick : undefined}
      className={`iicon iicon-${iconType}`} />
);