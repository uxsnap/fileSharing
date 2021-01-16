import React from 'react';
import { Icon, Row } from './index';

export const IconText = ({ iconType, text, onClick }) => {
  return (
    <div className="icon-text">
      <Row>
        <div>{text}</div>
        <Icon iconType={iconType} onClick={onClick}/>
      </Row>
    </div>
  )
};

export default IconText;