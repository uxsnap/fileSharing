import React from 'react';
import { Icon, Row } from './index';

export default ({ icon, text, onClick }) => {
  return (
    <div className="icon-text">
      <Row>
        <div>{text}</div>
        <Icon iconType={icon} onClick={onClick}/>
      </Row>
    </div>
  )
};