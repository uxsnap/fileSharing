import React, { useState } from 'react';
import { Icon } from './index';

export default ({ onIconClick, children}) => {
  const [active, setActive] = useState(false);

  const handleOnIconClick = () => {
    setActive(!active);
    onIconClick && onIconClick();
  };

  return (
    <div className={`side-info ${active ? 'active' : ''}`}>
      <div className="side-info__main">
        {children}
      </div>
      <div className="side-info__button">
        <Icon iconType="arrow" onClick={handleOnIconClick}/>
      </div>
    </div>
  );
};