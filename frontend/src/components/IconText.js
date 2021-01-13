import React from 'react';
import { Icon, Row } from './index';

export const IconText = ({ icon, text, onClick }) => {
  return (
    <div className="icon-text">
      <Row>
        <div>{text}</div>
        <Icon iconType={icon} onClick={onClick}/>
      </Row>
    </div>
  )
};

export default IconText;